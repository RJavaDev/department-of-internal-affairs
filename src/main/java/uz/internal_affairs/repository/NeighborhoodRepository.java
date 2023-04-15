package uz.internal_affairs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.internal_affairs.entity.Neighborhood;

public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Integer> {
}
