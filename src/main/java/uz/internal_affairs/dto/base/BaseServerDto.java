package uz.internal_affairs.dto.base;

import lombok.Getter;
import lombok.Setter;
import uz.internal_affairs.constants.EntityStatus;

@Getter
@Setter
public class BaseServerDto {
    private Long id;

    private EntityStatus status;
}
