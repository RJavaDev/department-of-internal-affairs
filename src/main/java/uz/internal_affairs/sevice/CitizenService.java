package uz.internal_affairs.sevice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.internal_affairs.common.util.DateUtil;
import uz.internal_affairs.common.util.SecurityUtils;
import uz.internal_affairs.dto.IIOCitizensDto;
import uz.internal_affairs.entity.CategoryEntity;
import uz.internal_affairs.entity.CitizenEntity;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.repository.CategoryRepository;
import uz.internal_affairs.repository.CitizenRepository;
import uz.internal_affairs.repository.UserRepository;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service("citizenService")
@RequiredArgsConstructor
public class CitizenService {

    private final CitizenRepository citizenRepository;
    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    public IIOCitizensDto saveCitizen(HttpServletRequest request, IIOCitizensDto dto) throws ParseException {
        if(dto == null || StringUtils.isEmpty(dto.getCategory())) return null;

        Optional<CategoryEntity> optCategory = categoryRepository.findByName(dto.getCategory());

        CitizenEntity entity = new CitizenEntity();
        if(optCategory.isPresent()){
            BeanUtils.copyProperties(dto, entity, "birthDate");
            entity.setBirtDate(DateUtils.parseDate(dto.getBirtDate(), DateUtil.PATTERN14));
            entity.setCategoryId(optCategory.get().getId());
            UserEntity user = userRepository.findByUsername(SecurityUtils.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found!"));
            if(dto.getId() != null){
                entity.setUpdatedDate(LocalDateTime.now());
                entity.setModifiedBy(user.getId());
            }
            else {
                entity.setCreatedDate(LocalDateTime.now());
                entity.setCreatedBy(user.getId());
            }
            citizenRepository.save(entity);
        }
        return entity.getDto(new IIOCitizensDto());
    }
}
