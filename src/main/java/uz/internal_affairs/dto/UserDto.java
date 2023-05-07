package uz.internal_affairs.dto;

import lombok.*;
import uz.internal_affairs.dto.base.BaseServerModifierDto;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.entity.role.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseServerModifierDto {
    private String firstname;
    private String lastname;
    private String middleName;
    private String birthDate;
    private Long regionId;                    // mahalla id
    private String locationInformation;       // joy xaqida tuliq malumot
    private String regionName;                // tuman
    private String neighborhoodName;          //mahalla
    private String phoneNumber;
    private String username;
    private String password;
    private UserScoreDto userScore;
    private Role role;
    private Long imageId;
    private String imageFileUrl;

    public UserEntity toEntity( String... ignoreProperties) {
        return super.toEntity(this, new UserEntity(), ignoreProperties);
    }

}
