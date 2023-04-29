package uz.internal_affairs.sevice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.internal_affairs.common.util.DateUtil;
import uz.internal_affairs.common.util.SecurityUtils;
import uz.internal_affairs.constants.EntityStatus;
import uz.internal_affairs.dto.citizen_cotegory.*;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.internal_affairs.common.util.CategoryEnum.*;
import static uz.internal_affairs.dto.citizen_cotegory.CaughtLostCitizenDto.caughtLostCitizenList;
import static uz.internal_affairs.dto.citizen_cotegory.CaughtWantedCitizenDto.caughtWantedCitizenDtoList;
import static uz.internal_affairs.dto.citizen_cotegory.HuntingWeaponsCitizenDto.huntingWeaponsCitizenDtoList;
import static uz.internal_affairs.dto.citizen_cotegory.IIOCitizensDto.IIOCitizenDtoList;
import static uz.internal_affairs.dto.citizen_cotegory.PROFCitizenDto.PROFCitizenDtoList;
import static uz.internal_affairs.dto.citizen_cotegory.StatementCitizenDto.statementCitizenDtoList;
import static uz.internal_affairs.dto.citizen_cotegory.TotalGuardsCitizenDto.totalGuardsCitizenDtoList;

@Service("citizenService")
@RequiredArgsConstructor
public class CitizenService {
    private final CitizenRepository citizenRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;


    public List<? extends BaseCitizenDto> getCategoryFilterList(HttpServletRequest request, FilterForm filterForm) {

        Map<String, Object> filterMap = filterForm.getFilter();
        String category = null;
        if (filterMap != null) {
            if (filterMap.containsKey("category"))
                category = MapUtils.getString(filterMap, "category");
        }

        List<CitizenInterface> pageCitizens = citizenRepository.list(category);

        if (!pageCitizens.isEmpty()) {
            if (category != null) {
                if (category.equals(IIO_CITIZEN.name())) {
                    return IIOCitizenDtoList(pageCitizens);
                } else if (category.equals(PROF_CITIZEN.name())) {
                    return PROFCitizenDtoList(pageCitizens);
                } else if (category.equals(STATEMENT.name())) {
                    return statementCitizenDtoList(pageCitizens);
                } else if (category.equals(CAUGHT_WANTED_CITIZEN.name())) {
                    return caughtWantedCitizenDtoList(pageCitizens);
                } else if (category.equals(CAUGHT_LOST_CITIZEN.name())) {
                    return caughtLostCitizenList(pageCitizens);
                } else if (category.equals(TOTAL_CHECKED_OBJECT_GUARDS.name())) {
                    return totalGuardsCitizenDtoList(pageCitizens);
                } else if (category.equals(CHECKED_HUNTING_WEAPONS.name())) {
                    return huntingWeaponsCitizenDtoList(pageCitizens);
                }
            } else {
                return AllCitizenDto.allCitizenDtoList(pageCitizens);
            }
        }



        return null;
    }

    public List<? extends BaseCitizenDto> getCategoryDateRegionFilter(HttpServletRequest request, FilterForm filterForm) {

        var filterMap = filterForm.getFilter();

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

        List<CitizenInterface> pageCitizens = citizenRepository.getCategoryDateRegionFilter(categoryId, regionId, startDate, endDate, filterForm.getStart(), filterForm.getLength());

        if (!pageCitizens.isEmpty()) {
            if (categoryId != null) {
                if (categoryId.equals(IIO_CITIZEN.getValue())) {
                    return IIOCitizenDtoList(pageCitizens);
                } else if (categoryId.equals(PROF_CITIZEN.getValue())) {
                    return PROFCitizenDtoList(pageCitizens);
                } else if (categoryId.equals(STATEMENT.getValue())) {
                    return statementCitizenDtoList(pageCitizens);
                } else if (categoryId.equals(CAUGHT_WANTED_CITIZEN.getValue())) {
                    return caughtWantedCitizenDtoList(pageCitizens);
                } else if (categoryId.equals(CAUGHT_LOST_CITIZEN.getValue())) {
                    return caughtLostCitizenList(pageCitizens);
                } else if (categoryId.equals(TOTAL_CHECKED_OBJECT_GUARDS.getValue())) {
                    return totalGuardsCitizenDtoList(pageCitizens);
                }
            } else {
                return AllCitizenDto.allCitizenDtoList(pageCitizens);
            }

        }
        return null;
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

    public IIOCitizensDto saveCitizen(HttpServletRequest request, IIOCitizensDto dto) throws ParseException {
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
        return entity.toIIOCitizenDto();
    }

    @Transactional
    public Boolean deleteIIOCitizen(Long id) {
        Integer numAffectedRows = citizenRepository.deleteCitizen(id);
        return numAffectedRows > 0;
    }

    public List<IIOCitizensDto> getWorkDone(String username) {
        List<CitizenEntity> myWorkDone = citizenRepository.getMyWorkDone(username);
        return myWorkDone.stream().map(e -> {
            IIOCitizensDto dto = new IIOCitizensDto();
            BeanUtils.copyProperties(e, dto, "birtDate");
            dto.setBirthDate(e.getBirthDate().toString());
            dto.setRegionId(regionRepository.findById(e.getRegionId()).orElse(new RegionEntity()).getId());
            dto.setCategory(categoryRepository.findById(e.getCategoryId()).orElse(new CategoryEntity()).getName());
            return dto;
        }).collect(Collectors.toList());

    }

    public List<IIOCitizensDto> rows(HttpServletRequest request, FilterForm filterForm) {
        var filterMap = filterForm.getFilter();
        String category = null;
        if (filterMap != null) {
            // some logic here
            if (filterMap.containsKey("category"))
                category = MapUtils.getString(filterMap, "category");
        }
        List<CitizenEntity> entities = citizenRepository.rows(category);
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
}
