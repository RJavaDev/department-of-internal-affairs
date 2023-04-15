package uz.internal_affairs.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import uz.internal_affairs.entity.role.Role;

import java.io.File;

@Data
public class UserDto {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private File image;
    private File  fingerprint;
    private Role role;
}
