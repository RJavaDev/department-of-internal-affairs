package uz.internal_affairs.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.internal_affairs.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

}
