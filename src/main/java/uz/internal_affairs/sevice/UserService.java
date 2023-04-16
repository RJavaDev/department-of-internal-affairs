package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserDto, UserEntity> {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public UserEntity add(UserDto userDto) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(userDto.getUsername());
        if (byUsername.isPresent()){
            throw new IllegalArgumentException();
        }
        UserEntity userEntity = UserEntity.of(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        //barmoq izi
        // rasim
        return userRepository.save(userEntity);
    }


    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public UserEntity update(Long id, UserDto userDto) {
        return null;
    }
}
