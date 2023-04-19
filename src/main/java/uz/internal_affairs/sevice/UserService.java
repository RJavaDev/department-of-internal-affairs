package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.internal_affairs.common.util.SecurityUtils;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.dto.UserScoreDto;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.repository.UserRepository;

import java.util.Optional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserDto, UserEntity> {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(getClass().getName());

    @Override
    public UserEntity add(UserDto userDto) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(userDto.getUsername());
        if (byUsername.isPresent()) {
            throw new IllegalArgumentException();
        }
        UserEntity userEntity = UserEntity.of(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        //barmoq izi
        // rasim
        return userRepository.save(userEntity);
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

    public Boolean updateUser(Long id, UserDto userDto) {
        UserEntity user = userRepository.findByUsername(
                SecurityUtils.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("curren user username not found!")
        );

        if(!StringUtils.equals(userDto.getUsername(), user.getUsername())) return false;

        log.atInfo().log("!Обновление... пользователя");

        user.forUpdate();
        if(!StringUtils.isEmpty(userDto.getFirstname())) user.setFirstname(userDto.getFirstname());
        if(!StringUtils.isEmpty(userDto.getLastname())) user.setLastname(userDto.getLastname());
//        userRepository.save(user);
        return true;
    }
}
