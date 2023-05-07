package uz.internal_affairs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.internal_affairs.entity.FileEntity;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long>, JpaSpecificationExecutor<FileEntity> {

    @Query("from FileEntity where status <> 'DELETED'")
    Optional<FileEntity> getFileById(Long fileId);
}
