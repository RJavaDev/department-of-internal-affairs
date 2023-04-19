package uz.internal_affairs.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.internal_affairs.entity.CitizenEntity;

import java.util.List;

public interface CitizenRepository extends JpaRepository<CitizenEntity, Long> {

    /**
     *
     * */
    @Query("from CitizenEntity c where c.status <> 'DELETED' and (?1 IS NULL OR (c.categoryEntity.name = ?1))")
    Page<CitizenEntity> rows(Pageable pageable, @Param("category") String category);

    @Query(value = "select count(*) from d_citizen dc where dc.status <> 'DELETED' AND (:category IS NULL OR (dc.category_id = (select id from d_category where name = :category)))", nativeQuery = true)
    Integer getTotal(@Param("category") String category);

    @Modifying
    @Query(value = "UPDATE d_citizen SET status = 'DELETED' where id = :id", nativeQuery = true)
    Integer deleteCitizen(@Param("id") Long id);
    @Query(value = "SELECT dc.* FROM d_citizen dc\n" +
            "     INNER JOIN d_user du ON du.id = :myUserId\n" +
            "    AND  du.id = dc.user_id\n" +
            "    AND dc.created_date >= date_trunc('month', current_timestamp AT TIME ZONE 'Asia/Tashkent')\n" +
            "    AND dc.created_date <= date_trunc('month', current_timestamp AT TIME ZONE 'Asia/Tashkent' + INTERVAL '1 month')\n" +
            "WHERE dc.status <> 'DELETE'",nativeQuery = true)
    List<CitizenEntity> getMyCitizenList(@Param("myUserId") Long myUserId);
}
