package uz.internal_affairs.entity.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import uz.internal_affairs.constants.EntityStatus;
import uz.internal_affairs.dto.base.BaseServerDto;

@Getter
@Setter
@MappedSuperclass
public class BaseServerEntity extends BaseObject{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private EntityStatus status;

    /**********************************
     ***    convert ENTITY to DTO   ***
     **********************************/
    public <ENTITY extends BaseServerEntity, DTO extends BaseServerDto> DTO entityToDto(ENTITY entity, DTO dto) {
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public Long getUniqueId() {
        return getId();
    }
}
