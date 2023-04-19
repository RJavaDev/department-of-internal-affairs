package uz.internal_affairs.dto;

import lombok.*;
import uz.internal_affairs.dto.base.BaseServerModifierDto;
import uz.internal_affairs.entity.UserEntity;
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
    private Date birtDate;
    private String phoneNumber;
    private String username;
    private String password;
    private UserScoreDto userScore;
    private Role role;
    private String image;
    private String fingerprint;

/*    public static UserDto of(UserEntity user) {
        return UserDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .middleName(user.getMiddleName())
                .birtDate(user.getBirtDate())
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .role(user.getRole())
                //image and barmoq izi
                .build();
    }*/
}
