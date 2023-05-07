package uz.internal_affairs.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.internal_affairs.constants.EntityStatus;
import uz.internal_affairs.constants.TableNames;
import uz.internal_affairs.dto.RegionDto;
import uz.internal_affairs.entity.base.BaseServerModifierEntity;

import java.util.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = TableNames.DEPARTMENT_REGION)
public class RegionEntity  extends BaseServerModifierEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "parentId")
    private Long parentId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    List<RegionEntity> children = new ArrayList<>();

    public RegionDto getDto(){
        return getDto(true);
    }


    public RegionDto getDto(boolean withChildren) {
        RegionDto dto = entityToDto(this, new RegionDto());
        if (this.getChildren() != null && withChildren) {
            dto.setChildren(
                    this.getChildren().stream()
                    .map(RegionEntity::getDto)
                    .filter(p -> p.getStatus() != EntityStatus.DELETED).collect(Collectors.toList()));
        }
        return dto;
    }

}
