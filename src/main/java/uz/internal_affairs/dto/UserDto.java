package uz.internal_affairs.dto;

import lombok.Data;
import uz.internal_affairs.entity.role.Role;

import java.util.Date;

@Data
public class UserDto {
    private String firstname;
    private String lastname;
    private String middleName;
    private Date birtDate;
    private String phoneNumber;
    private String username;
    private String password;
    private Role role;
    private String image;
    private String  fingerprint;


}
