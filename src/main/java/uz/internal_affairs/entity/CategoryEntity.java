package uz.internal_affairs.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.internal_affairs.constants.TableNames;
import uz.internal_affairs.dto.CategoryDto;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.entity.base.BaseServerModifierEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = TableNames.DEPARTMENT_CATEGORY)
public class CategoryEntity extends BaseServerModifierEntity {

    @Column(unique = true, nullable = false)
    private String name;
    private Integer score;

    public static CategoryEntity of(CategoryDto categoryDto){
        return CategoryEntity.builder()
                .name(categoryDto.getName())
                .score(categoryDto.getScore())
                .build();
    }

    /************************************************************
     * ******************** CONVERT TO DTO ***********************
     * ***********************************************************/
    public CategoryDto toDto(){
        return toDto(this, new CategoryDto());
    }
}
