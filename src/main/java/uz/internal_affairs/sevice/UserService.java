package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.internal_affairs.dto.UserDTO;
import uz.internal_affairs.entity.User;
import uz.internal_affairs.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserDTO, User> {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public User add(UserDTO userDto) {
        Optional<User> byUsername = userRepository.findByUsername(userDto.getUsername());
        if (byUsername.isPresent()){
            throw new IllegalArgumentException();
        }
        User user=User.of(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //barmoq izi
        // rasim
        return userRepository.save(user);
    }


    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public User update(Long id, UserDTO userDto) {
        return null;
    }
}
