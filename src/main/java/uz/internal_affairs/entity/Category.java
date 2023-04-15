package uz.internal_affairs.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.internal_affairs.dto.CategoryDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Category extends Base{

    @Column(unique = true, nullable = false)
    private String name;
    private Integer score;

    public static Category of(CategoryDTO categoryDto){
        return Category.builder()
                .name(categoryDto.getName())
                .score(categoryDto.getScore())
                .build();
    }
}
