package uz.internal_affairs.sevice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.internal_affairs.common.util.DateUtil;
import uz.internal_affairs.common.util.SecurityUtils;
import uz.internal_affairs.constants.EntityStatus;
import uz.internal_affairs.dto.citizen_cotegory.*;
import uz.internal_affairs.dto.response.DataGrid;
import uz.internal_affairs.dto.response.FilterForm;
import uz.internal_affairs.entity.CategoryEntity;
import uz.internal_affairs.entity.CitizenEntity;
import uz.internal_affairs.entity.RegionEntity;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.interfaces.CitizenInterface;
import uz.internal_affairs.repository.CategoryRepository;
import uz.internal_affairs.repository.CitizenRepository;
import uz.internal_affairs.repository.RegionRepository;
import uz.internal_affairs.repository.UserRepository;

import java.util.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.internal_affairs.common.util.CategoryEnum.*;

@Service("citizenService")
@RequiredArgsConstructor
public class CitizenService {
    private final CitizenRepository citizenRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;


    public DataGrid<Object> dataGrid(HttpServletRequest request, FilterForm filterForm) throws Exception {
        DataGrid<Object> dataGrid = new DataGrid<>();
        dataGrid.setRows(rows2(request, filterForm));
        dataGrid.setTotal(getTotal(filterForm));
        return dataGrid;
    }
    public DataGrid<AllCitizenDto> userJobs(Long userId,HttpServletRequest request, FilterForm filterForm) throws Exception {
        DataGrid<AllCitizenDto> dataGrid = new DataGrid<>();
        dataGrid.setRows(getWorkDone(userId,request, filterForm));
        dataGrid.setTotal(getTotal(filterForm));
        return dataGrid;
    }



    public List<IIOCitizensDto> rows(HttpServletRequest request, FilterForm filterForm) {
        Sort sort = orderSortField("id");
        Pageable pageable = pageable(sort, filterForm);
        Map<String, Object> filterMap = filterForm.getFilter();
        String category = null;
        if (filterMap != null) {
            // some logic here
            if (filterMap.containsKey("category"))
                category = MapUtils.getString(filterMap, "category");
        }
        Page<CitizenEntity> entities = citizenRepository.rows(pageable, category);
        List<IIOCitizensDto> list = new ArrayList<>();
        if (!entities.isEmpty()) {
            list = entities.stream().map(e -> {
                IIOCitizensDto dto = new IIOCitizensDto();
                BeanUtils.copyProperties(e, dto, "birthDate");
                dto.setBirthDate(DateUtil.format(e.getBirthDate(), DateUtil.PATTERN14));
//                if(e.getRegionId() != null) dto.setRegionAddress(regionRepository.getRegionRecursive(e.getRegionId()).get());
                return dto;
            }).collect(Collectors.toList());
        }
        return list;
    }

    public List<Object> rows2(HttpServletRequest request, FilterForm filterForm) {
        Sort sort = orderSortField("id");
        Pageable pageable = pageable(sort, filterForm);
        Map<String, Object> filterMap = filterForm.getFilter();
        String category = null;
        if (filterMap != null) {
            // some logic here
            if (filterMap.containsKey("category"))
                category = MapUtils.getString(filterMap, "category");
        }

        Page<CitizenInterface> pageCitizens = citizenRepository.list(category, pageable);

        if (!pageCitizens.isEmpty()) {
            if (category != null) {
                if (category.equals(IIO_CITIZEN.name())) {
                    return Collections.singletonList(interfaceToIIOCitizenDto(pageCitizens));
                } else if (category.equals(PROF_CITIZEN.name())) {
                    return Collections.singletonList(interfaceToPROFCitizenDto(pageCitizens));
                } else if (category.equals(STATEMENT.name())) {
                    return Collections.singletonList(interfaceToStatementCitizenDto(pageCitizens));
                } else if (category.equals(CAUGHT_WANTED_CITIZEN.name())) {
                    return Collections.singletonList(interfaceToCaughtWantedCitizenDto(pageCitizens));
                } else if (category.equals(CAUGHT_LOST_CITIZEN.name())) {
                    return Collections.singletonList(interfaceToCaughtLostCitizen(pageCitizens));
                } else if (category.equals(TOTAL_CHECKED_OBJECT_GUARDS.name())) {
                    return Collections.singletonList(entityToTotalGuardsCitizenDto(pageCitizens));
                } else if (category.equals(CHECKED_HUNTING_WEAPONS.name())) {
                    return Collections.singletonList(interfaceToHuntingWeaponsCitizenDto(pageCitizens));
                }
            } else {
                return Collections.singletonList(entityToAllCitizenDto(pageCitizens));
            }
        }



        return null;
    }

    public List<? extends BaseCitizenDto> getCategoryDateRegionFilter(HttpServletRequest request, FilterForm filterForm) {
        Sort sort = orderSortField("id");
        var pageable = pageable(sort, filterForm);
        Map<String, Object> filterMap = filterForm.getFilter();

        Long categoryId = null;
        Long regionId = null;
        Date startDate = null;
        Date endDate = null;

        if (filterMap != null) {

            if (filterMap.containsKey("categoryId")) {
                categoryId = MapUtils.getLong(filterMap, "categoryId");
            }
            if (filterMap.containsKey("regionId")) {
                regionId = MapUtils.getLong(filterMap, "regionId");
            }
            if (filterMap.containsKey("startDate")) {
                try {
                    startDate = DateUtils.parseDate((MapUtils.getString(filterMap, "startDate")), DateUtil.PATTERN1);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (filterMap.containsKey("endDate")) {
                try {
                    endDate = DateUtils.parseDate((MapUtils.getString(filterMap, "endDate")), DateUtil.PATTERN1);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

        }

        Page<CitizenInterface> pageCitizens = citizenRepository.getCategoryDateRegionFilter(categoryId, regionId, startDate, endDate, pageable);

        if (!pageCitizens.isEmpty()) {
            if (categoryId != null) {
                if (categoryId.equals(IIO_CITIZEN.getValue())) {
                    return interfaceToIIOCitizenDto(pageCitizens);
                } else if (categoryId.equals(PROF_CITIZEN.getValue())) {
                    return interfaceToPROFCitizenDto(pageCitizens);
                } else if (categoryId.equals(STATEMENT.getValue())) {
                    return interfaceToStatementCitizenDto(pageCitizens);
                } else if (categoryId.equals(CAUGHT_WANTED_CITIZEN.getValue())) {
                    return interfaceToCaughtWantedCitizenDto(pageCitizens);
                } else if (categoryId.equals(CAUGHT_LOST_CITIZEN.getValue())) {
                    return interfaceToCaughtLostCitizen(pageCitizens);
                } else if (categoryId.equals(TOTAL_CHECKED_OBJECT_GUARDS.getValue())) {
                    return entityToTotalGuardsCitizenDto(pageCitizens);
                }
            } else {
                entityToAllCitizenDto(pageCitizens);
            }

        }
        return null;
    }

    private List<AllCitizenDto> entityToAllCitizenDto(Page<CitizenInterface> pageCitizens) {
        List<AllCitizenDto> list = new ArrayList<>();
        for (CitizenInterface cInterface : pageCitizens) {
            AllCitizenDto dto = new AllCitizenDto();
            dto.setId(cInterface.getId());
            dto.setCreatedBy(cInterface.getCreated_by());
            dto.setCategoryId(cInterface.getCategory_id());
            dto.setPhoneNumber(cInterface.getPhone_number());
            dto.setFirstName(cInterface.getFirst_name());
            dto.setLastName(cInterface.getLast_name());
            dto.setBirthDate(DateUtil.format(cInterface.getBirth_date(), DateUtil.PATTERN14));
            dto.setMiddleName(cInterface.getMiddle_name());
            dto.setRegionAddress(cInterface.getCitizen_address());
            dto.setRegionId(cInterface.getRegion_id());
            dto.setLocationInformation(cInterface.getLocation_information());
            dto.setCauseOfEvent(cInterface.getCause_of_event());
            dto.setEmployeeSummary(cInterface.getEmployee_summary());
            dto.setPlaceOfImport(cInterface.getPlace_of_import());
            list.add(dto);
        }
        return list;
    }

    private List<TotalGuardsCitizenDto> entityToTotalGuardsCitizenDto(Page<CitizenInterface> pageCitizens) {
        List<TotalGuardsCitizenDto> citizenList = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            TotalGuardsCitizenDto dto = new TotalGuardsCitizenDto();
            dto.setId(citizen.getId());
            dto.setFirstName(citizen.getFirst_name());
            dto.setLastName(citizen.getLast_name());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setPhoneNumber(citizen.getPhone_number());
            dto.setCategoryId(citizen.getCategory_id());
            dto.setRegionId(citizen.getRegion_id());
            dto.setCategory(TOTAL_CHECKED_OBJECT_GUARDS.name());
            dto.setLocationInformation(citizen.getLocation_information());
            dto.setRegionAddress(citizen.getCitizen_address());
            dto.setLocationInformationObject(citizen.getLocation_information_object());
            citizenList.add(dto);
        }
        return citizenList;
    }

    private List<CaughtLostCitizenDto> interfaceToCaughtLostCitizen(Page<CitizenInterface> pageCitizens) {
        List<CaughtLostCitizenDto> citizenList = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            CaughtLostCitizenDto dto = new CaughtLostCitizenDto();
            dto.setId(citizen.getId());
            dto.setFirstName(citizen.getFirst_name());
            dto.setLastName(citizen.getLast_name());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setPhoneNumber(citizen.getPhone_number());
            dto.setCategoryId(citizen.getCategory_id());
            dto.setRegionId(citizen.getRegion_id());
            dto.setLocationInformation(citizen.getLocation_information());
            dto.setEmployeeSummary(citizen.getEmployee_summary());
            citizenList.add(dto);
        }
        return citizenList;
    }

    private List<CaughtWantedCitizenDto> interfaceToCaughtWantedCitizenDto(Page<CitizenInterface> pageCitizens) {
        List<CaughtWantedCitizenDto> caughtWantedCitizenDtoList = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            CaughtWantedCitizenDto dto = new CaughtWantedCitizenDto();
            dto.setId(citizen.getId());
            dto.setFirstName(citizen.getFirst_name());
            dto.setLastName(citizen.getLast_name());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setPhoneNumber(citizen.getPhone_number());
            dto.setCategoryId(citizen.getCategory_id());
            dto.setCategory(CAUGHT_WANTED_CITIZEN.name());
            dto.setRegionId(citizen.getRegion_id());
            dto.setRegionAddress(citizen.getCitizen_address());
            dto.setLocationInformation(citizen.getLocation_information());
            dto.setEmployeeSummary(citizen.getEmployee_summary());
            caughtWantedCitizenDtoList.add(dto);
        }
        return caughtWantedCitizenDtoList;
    }

    private List<StatementCitizenDto> interfaceToStatementCitizenDto(Page<CitizenInterface> pageCitizens) {
        List<StatementCitizenDto> citizenDtoList = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            StatementCitizenDto dto = new StatementCitizenDto();
            dto.setRegionId(citizen.getId());
            dto.setLastName(citizen.getLast_name());
            dto.setFirstName(citizen.getFirst_name());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setPhoneNumber(citizen.getPhone_number());
            dto.setCategory(STATEMENT.name());
            dto.setBirthDate(citizen.getBirth_date().toString());
            dto.setLocationInformation(citizen.getLocation_information());
            dto.setRegionAddress(citizen.getLocation_information());
            dto.setEmployeeSummary(citizen.getEmployee_summary());
            dto.setStatement(citizen.getStatement());
            dto.setRegionAddress(citizen.getCitizen_address());
            dto.setPlaceOfImport(citizen.getPlace_of_import());
            citizenDtoList.add(dto);
        }
        return citizenDtoList;
    }

    private List<PROFCitizenDto> interfaceToPROFCitizenDto(Page<CitizenInterface> pageCitizens) {
        List<PROFCitizenDto> profCitizenDtoList = new ArrayList<>();
        for (CitizenInterface cInterface : pageCitizens) {
            PROFCitizenDto dto = new PROFCitizenDto();
            dto.setLastName(cInterface.getLast_name());
            dto.setFirstName(cInterface.getFirst_name());
            dto.setMiddleName(cInterface.getMiddle_name());
            dto.setId(cInterface.getId());
            dto.setCategory(PROF_CITIZEN.name());
            dto.setCategoryId(cInterface.getCategory_id());
            dto.setCreatedDate(cInterface.getCreated_date());
            dto.setLocationInformation(cInterface.getLocation_information());
            dto.setRegionAddress(cInterface.getCitizen_address());
            dto.setStandUpPROF(cInterface.getStand_upprof());
            dto.setEmployeeSummary(cInterface.getEmployee_summary());
            dto.setPhoneNumber(cInterface.getPhone_number());
            profCitizenDtoList.add(dto);
        }
        return profCitizenDtoList;
    }

    private List<IIOCitizensDto> interfaceToIIOCitizenDto(Page<CitizenInterface> pageCitizens) {
        List<IIOCitizensDto> iIOCitizenList = new ArrayList<>();
        for (CitizenInterface cInterface : pageCitizens) {
            IIOCitizensDto dto = new IIOCitizensDto();
            dto.setId(cInterface.getId());
            dto.setCreatedBy(cInterface.getCreated_by());
            dto.setCategoryId(cInterface.getCategory_id());
            dto.setPhoneNumber(cInterface.getPhone_number());
            dto.setFirstName(cInterface.getFirst_name());
            dto.setLastName(cInterface.getLast_name());
            dto.setBirthDate(DateUtil.format(cInterface.getBirth_date(), DateUtil.PATTERN14));
            dto.setCategory(IIO_CITIZEN.name());
            dto.setMiddleName(cInterface.getMiddle_name());
            dto.setRegionAddress(cInterface.getCitizen_address());
            dto.setRegionId(cInterface.getRegion_id());
            dto.setLocationInformation(cInterface.getLocation_information());
            dto.setCauseOfEvent(cInterface.getCause_of_event());
            dto.setEmployeeSummary(cInterface.getEmployee_summary());
            dto.setPlaceOfImport(cInterface.getPlace_of_import());
            iIOCitizenList.add(dto);
        }
        return iIOCitizenList;
    }

    private List<HuntingWeaponsCitizenDto> interfaceToHuntingWeaponsCitizenDto(Page<CitizenInterface> pageCitizens) {
        List<HuntingWeaponsCitizenDto> citizenList = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            HuntingWeaponsCitizenDto dto = new HuntingWeaponsCitizenDto();
            dto.setId(citizen.getId());
            dto.setFirstName(citizen.getFirst_name());
            dto.setLastName(citizen.getLast_name());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setPhoneNumber(citizen.getPhone_number());
            dto.setCategoryId(citizen.getCategory_id());
            dto.setCategory(CHECKED_HUNTING_WEAPONS.name());
            dto.setHuntingWeaponModel(citizen.getHunting_weapon_model());
            dto.setHuntingWeaponCode(citizen.getHunting_weapon_code());
            dto.setEmployeeSummary(citizen.getEmployee_summary());
            citizenList.add(dto);
        }
        return citizenList;
    }


    @Transactional(readOnly = true)
    public Integer getTotal(FilterForm filterForm) {
        Map<String, Object> filter = filterForm.getFilter();
        String category = null;
        if (filter != null) {
            category = MapUtils.getString(filter, "category");
        }
        return citizenRepository.getTotal(category);
    }

    public AllCitizenDto saveCitizen(HttpServletRequest request, AllCitizenDto dto) throws ParseException {
        if (dto == null || StringUtils.isEmpty(dto.getCategory())) return null;

        Optional<CategoryEntity> optCategory = categoryRepository.findByName(dto.getCategory());

        CitizenEntity entity = new CitizenEntity();
        entity.forCreate();
        if (optCategory.isPresent()) {
            BeanUtils.copyProperties(dto, entity, "birthDate");
            entity.setBirthDate(DateUtils.parseDate(dto.getBirthDate(), DateUtil.PATTERN14));
            entity.setCategoryId(optCategory.get().getId());
            entity.setRegionId(dto.getRegionId());

            UserEntity user = userRepository.findByUsername(SecurityUtils.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found!"));
            if (dto.getId() != null) {
                entity.setUpdatedDate(LocalDateTime.now());
                entity.setModifiedBy(user.getId());
            } else {
                entity.setCreatedDate(LocalDateTime.now());
                entity.setCreatedBy(user.getId());
            }
            entity.setUserId(user.getId());
            entity.setStatus(EntityStatus.CREATED);
            citizenRepository.save(entity);
        }
        return entity.toAllCitizenDto();
    }

    @Transactional
    public Boolean deleteIIOCitizen(Long id) {
        Integer numAffectedRows = citizenRepository.deleteCitizen(id);
        return numAffectedRows > 0;
    }

    public List<AllCitizenDto> getWorkDone(Long userId,HttpServletRequest request, FilterForm filterForm) {
        List<AllCitizenDto> getUserJobList = new ArrayList<>();
        Sort sort = Sort.by(Sort.Order.by("id"));
        Pageable pageable = PageRequest.of(filterForm.getStart() / filterForm.getLength(), filterForm.getLength(), sort);
        Page<CitizenInterface> pageCitizens = citizenRepository.getUserJobs(userId,pageable);

        if(!pageCitizens.isEmpty()){
            for(CitizenInterface cInterface : pageCitizens){
                AllCitizenDto dto = new AllCitizenDto();
                dto.setId(cInterface.getId());
                dto.setCreatedBy(cInterface.getCreated_by());
                dto.setCategoryId(cInterface.getCategory_id());
                dto.setPhoneNumber(cInterface.getPhone_number());
                dto.setFirstName(cInterface.getFirst_name());
                dto.setLastName(cInterface.getLast_name());
                dto.setBirthDate(DateUtil.format(cInterface.getBirth_date(), DateUtil.PATTERN14));
                dto.setMiddleName(cInterface.getMiddle_name());
                dto.setRegionName(cInterface.getRegion_name());
                dto.setNeighborhoodName(cInterface.getNeighborhood_name());
                dto.setRegionId(cInterface.getRegion_id());
                dto.setLocationInformation(cInterface.getLocation_information());
                dto.setCauseOfEvent(cInterface.getCause_of_event());
                dto.setEmployeeSummary(cInterface.getEmployee_summary());
                dto.setPlaceOfImport(cInterface.getPlace_of_import());
                getUserJobList.add(dto);
            }
        }
        return getUserJobList;
    }

    public Sort orderSortField(String field) {
        return Sort.by(Sort.Order.by(field));
    }

    public Pageable pageable(Sort sort, FilterForm filterForm) {
        return PageRequest.of(filterForm.getStart() / filterForm.getLength(), filterForm.getLength(), sort);
    }
}
