package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.internal_affairs.common.util.SecurityUtils;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.dto.UserScoreDto;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.repository.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(getClass().getName());

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


    public UserDto getUserInformation(Long id) {

        UserEntity userInformation = userRepository.getUserInformation(id);
        UserDto responseInformationUser = userInformation.toDto();
        UserScoreDto responseUserScore = new UserScoreDto();

        responseUserScore.setUserScoreDay(userRepository.getUserScoreToday(id));
        responseUserScore.setUserScoreMonth(userRepository.getUserScoreMonth(id));

        responseInformationUser.setUserScore(responseUserScore);

        return responseInformationUser;
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public Boolean userDelete(Long id) {
        Integer userDeleteIsSuccess = userRepository.userDelete(id);
        return userDeleteIsSuccess > 0;
    }
}
