package uz.internal_affairs.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
}
