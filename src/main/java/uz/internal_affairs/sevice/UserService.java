package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.internal_affairs.common.util.DateUtil;
import uz.internal_affairs.common.util.SecurityUtils;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.dto.UserScoreDto;
import uz.internal_affairs.entity.RegionEntity;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.entity.role.Role;
import uz.internal_affairs.repository.RegionRepository;
import uz.internal_affairs.repository.UserRepository;

import java.text.ParseException;
import java.util.Optional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserDto, UserEntity> {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;

    private Logger log = LoggerFactory.getLogger(getClass().getName());

    @Override
    public UserEntity add(UserDto userDto) throws ParseException {

        Optional<UserEntity> byUsername = userRepository.findByUsername(userDto.getUsername());
        if (byUsername.isEmpty()) {
            UserEntity userEntity = UserEntity.builder()
                    .phoneNumber(userDto.getPhoneNumber())
                    .firstname(userDto.getFirstname())
                    .lastname(userDto.getLastname())
                    .middleName(userDto.getMiddleName())
                    .username(userDto.getUsername())
                    .neighborhoodId(userDto.getNeighborhoodId())
                    .regionId(userDto.getRegionId())
                    .build();
            userEntity.forCreate();
            userEntity.setBirtDate(DateUtils.parseDate(userDto.getBirtDate(), DateUtil.PATTERN14));
            userEntity.setRole(userDto.getRole() != Role.ADMIN ? Role.USER : Role.ADMIN);
            userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
            //barmoq izi
            // rasim
            return userRepository.save(userEntity);
        }
        throw new IllegalArgumentException();
    }

    public List<UserDto> getUserAll() {
        List<UserDto> userResponseDto = new ArrayList<>();
        List<UserEntity> userListDB = userRepository.getAllUser();
        userListDB.forEach((user) -> {
            userResponseDto.add(user.toDto("password"));
        });
        return userResponseDto;
    }

    public UserScoreDto getUserScore() {
        UserEntity currentUser = userRepository.findByUsername(SecurityUtils.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found!"));
        UserScoreDto userScoreDto = new UserScoreDto();
        userScoreDto.setUserScoreDay(userRepository.getUserScoreToday(currentUser.getId()));
        userScoreDto.setUserScoreMonth(userRepository.getUserScoreMonth(currentUser.getId()));
        return userScoreDto;
    }


    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public UserEntity update(Long id, UserDto userDto) {
        return null;
    }

    public UserDto getUserInformation(Long id) {

        UserEntity userInformation = userRepository.getUserInformation(id);
        UserDto responseInformationUser = userInformation.toDto();
        UserScoreDto responseUserScore = new UserScoreDto();

        responseUserScore.setUserScoreDay(userRepository.getUserScoreToday(id));
        responseUserScore.setUserScoreMonth(userRepository.getUserScoreMonth(id));

        responseInformationUser.setUserScore(responseUserScore);

        return responseInformationUser;
    }

    public Boolean updateUser(UserDto userDto) {
        UserEntity user = userRepository.findByUsername(
                SecurityUtils.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("curren user username not found!")
        );

        if (!Objects.equals(userDto.getId(), user.getId())) return false;

        log.atInfo().log("!Обновление... пользователя");

        user.forUpdate();

        if (!StringUtils.isEmpty(userDto.getFirstname())) user.setFirstname(userDto.getFirstname());
        if (!StringUtils.isEmpty(userDto.getLastname())) user.setLastname(userDto.getLastname());
        if (!StringUtils.isEmpty(userDto.getUsername())) user.setUsername(userDto.getUsername());
        if (!StringUtils.isEmpty(userDto.getPhoneNumber())) user.setPhoneNumber(user.getPhoneNumber());
        if (!StringUtils.isEmpty(userDto.getMiddleName())) user.setMiddleName(user.getMiddleName());
        if (!StringUtils.isEmpty(userDto.getPassword()))
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
        return true;
    }
}
