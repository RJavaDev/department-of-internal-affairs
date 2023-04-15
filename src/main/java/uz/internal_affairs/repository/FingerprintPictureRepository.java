package uz.internal_affairs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.internal_affairs.entity.FingerprintImage;

import java.util.Optional;

@Repository
public interface FingerprintPictureRepository extends JpaRepository<FingerprintImage,Long> {
    Optional<FingerprintImage> findByName(String name);
}
