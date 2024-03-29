package uz.internal_affairs.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.flywaydb.core.internal.database.DatabaseExecutionStrategy;
import org.springframework.beans.BeanUtils;
import uz.internal_affairs.constants.EntityStatus;
import uz.internal_affairs.dto.base.BaseServerModifierDto;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass

public class BaseServerModifierEntity extends BaseServerEntity{
    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;

    @Column(name = "createdBy")
    private Long createdBy;

    @Column(name = "modifiedBy")
    private Long modifiedBy;

    public void forCreate(){
        forCreate(null);
    }
    public void forCreate(Long creatorId){
        this.setCreatedBy(creatorId);
        this.setCreatedDate(LocalDateTime.now());
        this.setStatus(EntityStatus.CREATED);
    }

    public void forUpdate(){
        forUpdate(null);
    }

    public void forUpdate(Long modifierId){
        this.setModifiedBy(modifierId);
        this.setUpdatedDate(LocalDateTime.now());
        this.setStatus(EntityStatus.UPDATED);
    }

    /*
    ********************* CONVERT TO DTO ****************************
    * */
    public <ENTITY extends BaseServerModifierEntity, DTO extends BaseServerModifierDto> DTO toDto(ENTITY entity, DTO dto, String... ignoreProperties){
        BeanUtils.copyProperties(entity, dto, ignoreProperties);
        return dto;
    }
}
