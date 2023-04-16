package uz.internal_affairs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.internal_affairs.entity.CitizenEntity;

public interface CitizenRepository extends JpaRepository<CitizenEntity, Long> {
}
