package uz.internal_affairs.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.internal_affairs.entity.CitizenEntity;

public interface CitizenRepository extends JpaRepository<CitizenEntity, Long> {

    @Query("from CitizenEntity c where c.status <> 'DELETED' and (?1 IS NULL OR (c.categoryEntity.name = ?1))")
    Page<CitizenEntity> rows(Pageable pageable, @Param("category") String category);

    @Query(value = "select count(*) from d_citizen dc where dc.status <> 'DELETED' AND (:category IS NULL OR (dc.category_id = (select id from d_category where name = :category)))", nativeQuery = true)
    Integer getTotal(@Param("category") String category);
}
