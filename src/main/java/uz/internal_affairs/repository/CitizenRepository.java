package uz.internal_affairs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.internal_affairs.entity.Citizen;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {
}
