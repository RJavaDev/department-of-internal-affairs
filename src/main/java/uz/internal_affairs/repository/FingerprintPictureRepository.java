package uz.internal_affairs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.internal_affairs.entity.FileEntity;

import java.util.Optional;

@Repository
public interface FingerprintPictureRepository extends JpaRepository<FileEntity,Long> {
    Optional<FileEntity> findByName(String name);
}
