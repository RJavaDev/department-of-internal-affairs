package uz.internal_affairs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.internal_affairs.dto.TokenResponseDto;
import uz.internal_affairs.sevice.AuthenticationService;
import uz.internal_affairs.dto.LoginRequestDto;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.entity.role.Role;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> register(
            @RequestBody UserDto userDto
    ) {
        userDto.setRole(Role.USER);
        return ResponseEntity.ok(service.register(userDto));
    }
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/admin/register")
    public ResponseEntity<TokenResponseDto> adminRegister(
            @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(service.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authenticate(
            @RequestBody LoginRequestDto request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
