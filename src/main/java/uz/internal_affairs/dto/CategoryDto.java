package uz.internal_affairs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.internal_affairs.dto.base.BaseServerModifierDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto extends BaseServerModifierDto {
    private String name;
    private Integer score;
}
