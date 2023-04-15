package uz.internal_affairs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.internal_affairs.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Integer> {
}
