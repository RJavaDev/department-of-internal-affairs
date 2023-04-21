package uz.internal_affairs.sevice;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uz.internal_affairs.config.token.JwtService;
import uz.internal_affairs.constants.EntityStatus;
import uz.internal_affairs.dto.TokenResponseDto;
import uz.internal_affairs.dto.LoginRequestDto;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.entity.role.Role;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponseDto register(UserDto request)  {
        String jwtToken=null;
        try {
            jwtToken = jwtService.generateToken(userService.add(request));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return TokenResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public TokenResponseDto authenticate(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserEntity user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found!"));
        String jwtToken = jwtService.generateToken(user);
        return TokenResponseDto.builder()
                .token(jwtToken)
                .build();
    }

}
