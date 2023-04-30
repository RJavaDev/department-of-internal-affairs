package uz.internal_affairs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.internal_affairs.entity.role.Role;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {

  private String token;

  private List<Role> allowedRoles;
}
