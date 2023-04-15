package uz.internal_affairs.entity;

import jakarta.persistence.*;
//import uz.internal_affairs.config.token.Token;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.entity.role.Role;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User extends  Base implements UserDetails {

  private String firstname;
  private String lastname;
  private String middleName;
  private Date birtDate;
  private String phoneNumber;
  @Column(unique = true,nullable = false)
  private String username;
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;
  @OneToOne
  private FingerprintImage fingerprintImage;

  public static User of(UserDto userDto){
    return User.builder()
            .birtDate(userDto.getBirtDate())
            .phoneNumber(userDto.getPhoneNumber())
            .firstname(userDto.getFirstname())
            .lastname(userDto.getLastname())
            .middleName(userDto.getMiddleName())
            .username(userDto.getUsername())
            .role(userDto.getRole()== null ? Role.USER: Role.valueOf(userDto.getRole()))
            .build();

  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
