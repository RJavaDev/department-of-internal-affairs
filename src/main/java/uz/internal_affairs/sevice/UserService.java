package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.internal_affairs.common.util.DateUtil;
import uz.internal_affairs.common.util.SecurityUtils;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.dto.UserScoreDto;
import uz.internal_affairs.entity.RegionEntity;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.interfaces.CitizenInterface;
import uz.internal_affairs.repository.RegionRepository;
import uz.internal_affairs.repository.UserRepository;

import java.text.ParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private Logger log = LoggerFactory.getLogger(getClass().getName());

    public List<UserDto> getUserAll() {
        List<UserDto> userResponseDto = new ArrayList<>();
        List<UserEntity> userListDB = userRepository.getAllUser();
        List<RegionEntity> all = regionRepository.findAll();
        userListDB.forEach((user) -> {
            UserDto userDto = user.toDto("password", "birtDate");
            userDto.setRegionName(all.get(0).getName());
            userDto.setNeighborhoodName(all.get(Math.toIntExact(user.getRegionId() - 1)).getName());
            userDto.setBirtDate(user.getBirtDate() == null ? null : user.getBirtDate().toString());
            userResponseDto.add(userDto);
        });
        return userResponseDto;

    }

    public UserScoreDto getUserScore() {
        UserEntity currentUser = userRepository.findByUsername(SecurityUtils.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Not found!"));
        UserScoreDto userScoreDto = new UserScoreDto();
        userScoreDto.setUserScoreDay(userRepository.getUserScoreToday(currentUser.getId()));
        userScoreDto.setUserScoreMonth(userRepository.getUserScoreMonth(currentUser.getId()));
        return userScoreDto;
    }

    public UserDto getUserInformation(Long id) {
        UserEntity userInformation = userRepository.getUserInformation(id);
        CitizenInterface regionAndNeighborhood = regionRepository.getRegionAndNeighborhood(userInformation.getRegionId());
        UserDto responseInformationUser = userInformation.toDto();
        responseInformationUser.setBirtDate(userInformation.getBirtDate()==null ? null :userInformation.getBirtDate().toString());
        responseInformationUser.setNeighborhoodName(regionAndNeighborhood.getNeighborhood_name());
        responseInformationUser.setRegionName(regionAndNeighborhood.getRegion_name());
        UserScoreDto responseUserScore = new UserScoreDto();
        responseUserScore.setUserScoreDay(userRepository.getUserScoreToday(id));
        responseUserScore.setUserScoreMonth(userRepository.getUserScoreMonth(id));
        responseInformationUser.setUserScore(responseUserScore);

        return responseInformationUser;
    }

    @Transactional
    public Boolean updateUser(UserDto userDto) {
        UserEntity user = userRepository.findByUsername(
                SecurityUtils.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("curren user username not found!")
        );

        log.atInfo().log("!Обновление... пользователя");
        user.forUpdate();
        if (!StringUtils.isEmpty(userDto.getFirstname())) user.setFirstname(userDto.getFirstname());
        if (!StringUtils.isEmpty(userDto.getLastname())) user.setLastname(userDto.getLastname());
        if (!StringUtils.isEmpty(userDto.getUsername())) user.setUsername(userDto.getUsername());
        if (!StringUtils.isEmpty(userDto.getPhoneNumber())) user.setPhoneNumber(userDto.getPhoneNumber());
        if (!StringUtils.isEmpty(userDto.getMiddleName())) user.setMiddleName(userDto.getMiddleName());
        if (!StringUtils.isEmpty(userDto.getPassword()))
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (!StringUtils.isEmpty(userDto.getBirtDate())) {
            try {
                user.setBirtDate(DateUtils.parseDate(userDto.getBirtDate(), DateUtil.PATTERN14));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        if (!StringUtils.isEmpty(String.valueOf(userDto.getRegionId()))) user.setRegionId(userDto.getRegionId());
        // rasmi qoldi
        userRepository.save(user);
        return true;
    }

    @Transactional
    public Boolean userDelete(Long id) {
        Integer userDeleteIsSuccess = userRepository.userDelete(id);
        return userDeleteIsSuccess > 0;
    }
}
