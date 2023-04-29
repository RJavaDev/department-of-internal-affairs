package uz.internal_affairs.entity;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.internal_affairs.constants.TableNames;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.entity.base.BaseServerModifierEntity;
import uz.internal_affairs.entity.role.Role;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.DEPARTMENT_USER)
public class UserEntity extends BaseServerModifierEntity implements UserDetails {

  private String firstname;
  private String lastname;
  private String middlename;
  private Date birtDate;
  private String phoneNumber;
  @Column(unique = true,nullable = false)
  private String username;
  private String password;
  @Column(name = "regionId", nullable = false)
  private Long regionId;
  @ManyToOne()
  @JoinColumn(name = "regionId", insertable = false, updatable = false)
  private RegionEntity region;

  @Enumerated(EnumType.STRING)
  private Role role;
  @OneToOne
  @JoinColumn(name = "fileId")
  private FileEntity file;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.asList(new SimpleGrantedAuthority(role.name()));
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


    /************************************************************
    * ******************** CONVERT TO DTO ***********************
    * ***********************************************************/
    public UserDto toDto(String... ignoreProperties){
      return toDto(this, new UserDto(), ignoreProperties);
    }
}
