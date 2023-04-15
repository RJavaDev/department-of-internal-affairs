package uz.internal_affairs.sevice;

import org.springframework.stereotype.Service;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.entity.User;

@Service
public class UserService implements BaseService<UserDto, User> {
    @Override
    public User add(UserDto userDto) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public User update(Long id, UserDto userDto) {
        return null;
    }
}
