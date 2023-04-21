package uz.internal_affairs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.internal_affairs.entity.RegionEntity;



public interface RegionRepository extends JpaRepository<RegionEntity,Long> {
}
