package uz.internal_affairs.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.internal_affairs.constants.EntityStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseServerDto {
    private Long id;

    private EntityStatus status;
}
