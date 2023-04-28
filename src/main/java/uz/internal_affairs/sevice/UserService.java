package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.internal_affairs.common.util.DateUtil;
import uz.internal_affairs.common.util.SecurityUtils;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.dto.UserScoreDto;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.repository.UserRepository;

import java.text.ParseException;
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
            var userAdd = user.toDto("password", "birtDate");
            userAdd.setBirtDate(String.valueOf(user.getBirtDate()));
            userResponseDto.add(userAdd);
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
        UserDto responseInformationUser = userInformation.toDto("password", "birtDate");
        responseInformationUser.setBirtDate(String.valueOf(userInformation.getBirtDate()));
        UserScoreDto responseUserScore = new UserScoreDto();
        responseUserScore.setUserScoreDay(userRepository.getUserScoreToday(id));
        responseUserScore.setUserScoreMonth(userRepository.getUserScoreMonth(id));
        responseInformationUser.setUserScore(responseUserScore);

        return responseInformationUser;
    }


    /**
     readOnly = true  bu degani bazani faqat o'qish uchun
     */
    @Transactional
    public Boolean updateUser(UserDto userDto) throws ParseException {
        UserEntity userDB = userRepository.findByUsername(
                SecurityUtils.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("curren user username not found!")
        );

        log.atInfo().log("!Обновление... пользователя");
        userDB.forUpdate(userDB.getId());

        userDB.setId(userDB.getId());
        userDB.setFirstname(userDto.getFirstname() != null ? userDto.getFirstname() : userDB.getFirstname());
        userDB.setLastname(userDto.getLastname() != null ? userDto.getLastname() : userDB.getLastname());
        userDB.setMiddleName(userDto.getMiddleName() != null ? userDto.getMiddleName() : userDB.getMiddleName());
        userDB.setUsername(userDto.getUsername() != null ? userDto.getUsername() : userDB.getUsername());
        userDB.setRegionId(userDto.getRegionId()!=null?userDto.getRegionId():userDB.getRegionId());
        userDB.setPassword(userDto.getPassword()!=null ? passwordEncoder.encode(userDto.getPassword()): userDB.getPassword());
        userDB.setPhoneNumber(userDto.getPhoneNumber() != null ? userDto.getPhoneNumber() : userDB.getPhoneNumber());
        userDB.setBirtDate(userDto.getBirtDate()!=null?(DateUtils.parseDate(userDto.getBirtDate(), DateUtil.PATTERN14)):userDB.getBirtDate());

        userRepository.save(userDB);
        return true;
    }

    @Transactional
    public Boolean userDelete(Long id) {
        Integer userDeleteIsSuccess = userRepository.userDelete(id);
        return userDeleteIsSuccess > 0;
    }
}
