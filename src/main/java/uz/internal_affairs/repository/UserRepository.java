package uz.internal_affairs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.interfaces.UserInterface;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM d_user du WHERE du.username = :username AND du.status <> 'DELETED'", nativeQuery = true)
    Optional<UserEntity> findByUsername(@Param("username") String username);

    /**
     * This method calculates a daily score for the user
     */
    @Query(value = "SELECT SUM(d_cg.score)\n" +
            "FROM d_citizen dc\n" +
            "  INNER JOIN d_user du\n" +
            "    ON du.id = :myUserId\n" +
            "      AND dc.user_id = du.id\n" +
            "      AND dc.created_date >= date_trunc('day', current_timestamp AT TIME ZONE 'Asia/Tashkent')\n" +
            "      AND dc.created_date <= date_trunc('day', current_timestamp AT TIME ZONE 'Asia/Tashkent' + INTERVAL '1 day')\n" +
            "  INNER JOIN d_category d_cg\n" +
            "    ON dc.category_id = d_cg.id", nativeQuery = true)
    Integer getUserScoreToday(@Param("myUserId") Long myUserId);


    /**
     * This method calculates the monthly score for the user
     */
    @Query(value = "SELECT SUM(d_cg.score)\n" +
            "FROM d_citizen dc\n" +
            "  INNER JOIN d_user du\n" +
            "    ON du.id = :myUserId\n" +
            "      AND dc.user_id = du.id\n" +
            "      AND dc.created_date >= date_trunc('month', current_timestamp AT TIME ZONE 'Asia/Tashkent')\n" +
            "      AND dc.created_date <= date_trunc('month', current_timestamp AT TIME ZONE 'Asia/Tashkent' + INTERVAL '1 month')\n" +
            "  INNER JOIN d_category d_cg\n" +
            "    ON dc.category_id = d_cg.id", nativeQuery = true)
    Integer getUserScoreMonth(@Param("myUserId") Long myUserId);

    @Query(value = "SELECT * FROM d_user WHERE status != 'DELETED'", nativeQuery = true)
    List<UserEntity> getAllUser();

    @Query(value = "SELECT * FROM d_user WHERE id = :userInformationId AND status <> 'DELETED'", nativeQuery = true)
    UserEntity getUserInformation(@Param("userInformationId") Long id);

    @Modifying
    @Query(value = "UPDATE d_user SET status = 'DELETED' WHERE id = :userId", nativeQuery = true)
    Integer userDelete(@Param("userId") Long userId);


    @Query(value =  "WITH regions AS(\n" +
            "    SELECT dr1.id as id,\n" +
            "           dr2.id as parent_id,\n" +
            "           dr1.name as name,\n" +
            "           dr2.name as parent_name\n" +
            "    FROM d_region dr1\n" +
            "    LEFT JOIN d_region dr2 ON dr1.parent_id = dr2.id\n" +
            "    WHERE dr1.parent_id IS NOT NULL\n" +
            ")\n" +
            "SELECT du.*,\n" +
            "       r.parent_id as parent_region_id,\n" +
            "       r.name as region_name,\n" +
            "       r.parent_name as parent_region_name\n" +
            "FROM d_user du\n" +
            "LEFT JOIN regions r ON du.region_id = ANY(ARRAY[r.id, r.parent_id])\n" +
            "where du.status <> 'DELETED';", nativeQuery = true)
    List<UserInterface> getAllUserInterface();

}
