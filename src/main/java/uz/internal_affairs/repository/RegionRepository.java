package uz.internal_affairs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.internal_affairs.entity.RegionEntity;
import uz.internal_affairs.interfaces.CitizenInterface;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<RegionEntity,Long> {



    @Query(value = "SELECT dr_ch.name AS region_name, dr_p.name AS neighborhood_name\n" +
            "FROM d_region dr_ch\n" +
            "         INNER JOIN d_region dr_p\n" +
            "                    ON dr_p.id=dr_ch.parent_id AND dr_ch.id= :regionId",nativeQuery = true)
    CitizenInterface getRegionAndNeighborhood(@Param("regionId") Long regionId);
    List<RegionEntity> findAllByParentId(Long parentId);

    Optional<RegionEntity> findByName(String name);

    @Query(value = "from RegionEntity where status <> 'DELETED'")
    List<RegionEntity> allValidRegions();
}
