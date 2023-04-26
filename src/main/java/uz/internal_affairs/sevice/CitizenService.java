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
import uz.internal_affairs.dto.citizen_cotegory.AllCitizenDto;
import uz.internal_affairs.dto.citizen_cotegory.IIOCitizensDto;
import uz.internal_affairs.dto.response.DataGrid;
import uz.internal_affairs.dto.response.FilterForm;
import uz.internal_affairs.entity.CategoryEntity;
import uz.internal_affairs.entity.CitizenEntity;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.interfaces.CitizenInterface;
import uz.internal_affairs.repository.CategoryRepository;
import uz.internal_affairs.repository.CitizenRepository;
import uz.internal_affairs.repository.UserRepository;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("citizenService")
@RequiredArgsConstructor
public class CitizenService {
    private final CitizenRepository citizenRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    public DataGrid<IIOCitizensDto> dataGrid(HttpServletRequest request, FilterForm filterForm) throws Exception {
        DataGrid<IIOCitizensDto> dataGrid = new DataGrid<>();
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
        Sort sort = Sort.by(Sort.Order.by("id"));
        Pageable pageable = PageRequest.of(filterForm.getStart() / filterForm.getLength(), filterForm.getLength(), sort);
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

    public List<IIOCitizensDto> rows2(HttpServletRequest request, FilterForm filterForm) {
        Sort sort = Sort.by(Sort.Order.by("id"));
        Pageable pageable = PageRequest.of(filterForm.getStart() / filterForm.getLength(), filterForm.getLength(), sort);
        Map<String, Object> filterMap = filterForm.getFilter();
        String category = null;
        if (filterMap != null) {
            // some logic here
            if (filterMap.containsKey("category"))
                category = MapUtils.getString(filterMap, "category");
        }

        Page<CitizenInterface> pageCitizens = citizenRepository.list(category, pageable);
        List<IIOCitizensDto> list = new ArrayList<>();
        if (!pageCitizens.isEmpty()) {
            for(CitizenInterface cInterface : pageCitizens){
                IIOCitizensDto dto = new IIOCitizensDto();
                dto.setId(cInterface.getId());
                dto.setCreatedBy(cInterface.getCreated_by());
//                dto.setStandUpPROF(cInterface.getStand_up_PROF());
//                dto.setLocationInformationObject(cInterface.getLocation_information_object());
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
        }
        return list;
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
}
