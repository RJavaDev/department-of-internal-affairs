package uz.internal_affairs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.internal_affairs.entity.RegionEntity;
import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<RegionEntity,Long> {

    List<RegionEntity> findAllByParentId(Long parentId);

    Optional<RegionEntity> findByName(String name);
}
