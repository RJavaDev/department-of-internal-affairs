package uz.internal_affairs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.internal_affairs.entity.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
      Optional<CategoryEntity>findByName(String name);
      Optional<CategoryEntity>findById(Long id);
}
