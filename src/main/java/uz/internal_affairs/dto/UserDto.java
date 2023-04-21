package uz.internal_affairs.dto;

import lombok.*;
import org.springframework.beans.BeanUtils;
import uz.internal_affairs.dto.base.BaseServerModifierDto;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.entity.base.BaseServerModifierEntity;
import uz.internal_affairs.entity.role.Role;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends BaseServerModifierDto {
    private String firstname;
    private String lastname;
    private String middleName;
    private String birtDate;
    private String regionId;
    private String neighborhoodId;
    private String phoneNumber;
    private String username;
    private String password;
    private UserScoreDto userScore;
    private Role role;
    private String image;
    private String fingerprint;


    public UserEntity toEntity( String... ignoreProperties) {
        return super.toEntity(this, new UserEntity(), ignoreProperties);
    }

}
