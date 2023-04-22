package uz.internal_affairs.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.internal_affairs.entity.CitizenEntity;
import uz.internal_affairs.interfaces.CitizenInterface;

import java.util.List;
import java.util.Optional;

public interface CitizenRepository extends JpaRepository<CitizenEntity, Long> {

    /**
     *
     * */
    @Query("from CitizenEntity c where c.status <> 'DELETED' and (?1 IS NULL OR (c.categoryEntity.name = ?1))")
    Page<CitizenEntity> rows(Pageable pageable, @Param("category") String category);

    @Query(value = "SELECT\n" +
                   "      dc.*,\n" +
                   "    (SELECT\n" +
                   "        STRING_AGG(name, ', ') AS citizen_address\n" +
                   "    FROM\n" +
                   "        (WITH RECURSIVE cte AS(\n" +
                   "            SELECT dr.id, dr.name, 0 as level, dr.parent_id\n" +
                   "            FROM d_region dr\n" +
                   "            WHERE dr.parent_id IS NOT NULL and dr.id = dc.region_id\n" +
                   "            UNION\n" +
                   "                   SELECT dr2.id, dr2.name AS name, cte.level + 1 AS level, dr2.parent_id\n" +
                   "                   FROM d_region dr2\n" +
                   "                   INNER JOIN cte ON dr2.id = cte.parent_id\n" +
                   "        )\n" +
                   "         SELECT *\n" +
                   "         FROM cte\n" +
                   "         ORDER BY level DESC\n" +
                   "        ) AS citezen_address)\n" +
                   "\n" +
                   "    from d_citizen dc\n" +
                   "left join d_region dr on dr.id = dc.region_id\n" +
                   "where (:category IS NULL OR dc.category_id = (select id from d_category where name = :category)) and dc.status <> 'DELETED'",
                   countProjection = "SELECT count(*) FROM d_citizen WHERE (:category IS NULL OR dc.category_id = (select id from d_category where name = :category)) and dc.status <> 'DELETED'",
                   nativeQuery = true)
    Page<CitizenInterface> list(@Param("category") String category, Pageable pageable);

    @Query(value = "select count(*) from d_citizen dc where dc.status <> 'DELETED' AND (:category IS NULL OR (dc.category_id = (select id from d_category where name = :category)))", nativeQuery = true)
    Integer getTotal(@Param("category") String category);

    @Modifying
    @Query(value = "UPDATE d_citizen SET status = 'DELETED' where id = :id", nativeQuery = true)
    Integer deleteCitizen(@Param("id") Long id);


    @Query(value = "SELECT dc.* FROM d_citizen dc\n" +
            "     INNER JOIN d_user du ON du.username = :myUsername \n" +
            "    AND  du.id = dc.user_id\n" +
            "    AND dc.created_date >= date_trunc('month', current_timestamp AT TIME ZONE 'Asia/Tashkent')\n" +
            "    AND dc.created_date <= date_trunc('month', current_timestamp AT TIME ZONE 'Asia/Tashkent' + INTERVAL '1 month')\n" +
            "WHERE dc.status <> 'DELETE'",nativeQuery = true)
    List<CitizenEntity> getMyWorkDone(@Param("myUsername") String myUsername);
}
