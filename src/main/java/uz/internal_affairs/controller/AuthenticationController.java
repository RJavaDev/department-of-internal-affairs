package uz.internal_affairs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.internal_affairs.dto.TokenResponseDTO;
import uz.internal_affairs.sevice.AuthenticationService;
import uz.internal_affairs.dto.LoginRequestDTO;
import uz.internal_affairs.dto.UserDTO;
import uz.internal_affairs.entity.role.Role;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(
            @RequestBody UserDTO userDto
    ) {
        userDto.setRole(Role.USER);
        return ResponseEntity.ok(service.register(userDto));
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/admin/register")
    public ResponseEntity<TokenResponseDTO> adminRegister(
            @RequestBody UserDTO userDto
    ) {
        return ResponseEntity.ok(service.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> authenticate(
            @RequestBody LoginRequestDTO request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
