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
    private String birtDate;
    private Long regionId;                    // mahalla id
    private String locationInformation;       // joy xaqida tuliq malumot
    private String regionName;                // tuman
    private String neighborhoodName;          //mahalla
    private String phoneNumber;
    private String username;
    private String password;
    private UserScoreDto userScore;
    private Role role;
    private String image;
    private Long fingerprintId;
    private String fingerPrintFileUrl;

    public UserEntity toEntity( String... ignoreProperties) {
        return super.toEntity(this, new UserEntity(), ignoreProperties);
    }

}
