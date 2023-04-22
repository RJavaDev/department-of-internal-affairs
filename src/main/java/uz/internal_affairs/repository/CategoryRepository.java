package uz.internal_affairs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.internal_affairs.entity.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
      Optional<CategoryEntity>findByName(String name);

}
