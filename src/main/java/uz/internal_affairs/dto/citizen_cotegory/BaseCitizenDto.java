package uz.internal_affairs.dto.citizen_cotegory;

import lombok.Getter;
import lombok.Setter;
import uz.internal_affairs.dto.base.BaseServerModifierDto;

@Getter
@Setter
public class BaseCitizenDto extends BaseServerModifierDto {

    private String firstName;

    private String lastName;

    private String middleName;

    private String phoneNumber;

    private String category;            // category name

    private Long categoryId;
}
