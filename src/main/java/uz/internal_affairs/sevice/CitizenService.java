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
import uz.internal_affairs.dto.citizen_cotegory.IIOCitizensDto;
import uz.internal_affairs.dto.response.DataGrid;
import uz.internal_affairs.dto.response.FilterForm;
import uz.internal_affairs.entity.CategoryEntity;
import uz.internal_affairs.entity.CitizenEntity;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.repository.CategoryRepository;
import uz.internal_affairs.repository.CitizenRepository;
import uz.internal_affairs.repository.UserRepository;

import java.util.*;
import java.text.ParseException;
import java.time.LocalDateTime;
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
        dataGrid.setRows(rows(request, filterForm));
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
                return dto;
            }).collect(Collectors.toList());
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

    public IIOCitizensDto saveCitizen(HttpServletRequest request, IIOCitizensDto dto) throws ParseException {
        if (dto == null || StringUtils.isEmpty(dto.getCategory())) return null;

        Optional<CategoryEntity> optCategory = categoryRepository.findByName(dto.getCategory());

        CitizenEntity entity = new CitizenEntity();
        entity.forCreate();
        if (optCategory.isPresent()) {
            BeanUtils.copyProperties(dto, entity, "birthDate");
            entity.setBirtDate(DateUtils.parseDate(dto.getBirtDate(), DateUtil.PATTERN14));
            entity.setCategoryId(optCategory.get().getId());
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
            BeanUtils.copyProperties(e, dto, "birthDate");
            return dto;
        }).collect(Collectors.toList());
    }
}
