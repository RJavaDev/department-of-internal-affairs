package uz.internal_affairs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.internal_affairs.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
      Optional<Category>findByName(String name);
      Optional<Category>findById(Long id);
}
