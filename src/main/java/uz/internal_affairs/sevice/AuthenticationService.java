package uz.internal_affairs.sevice;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uz.internal_affairs.common.util.DateUtil;
import uz.internal_affairs.common.util.SecurityUtils;
import uz.internal_affairs.config.token.JwtService;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public TokenResponseDto register(UserDto request) {
        String jwtToken = jwtService.generateToken(saveUser(request));
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

    public UserEntity saveUser(UserDto userDto){
        Optional<UserEntity> byUsername = userRepository.findByUsername(userDto.getUsername());
        if (byUsername.isPresent()) {
            throw new IllegalArgumentException();
        }
        UserEntity user = userDto.toEntity("password","role");
        try {
            user.forCreate(userRepository.findByUsername(SecurityUtils.getUsername()).orElseThrow(()-> new UsernameNotFoundException(" user name not found!")).getId());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setBirtDate(DateUtils.parseDate(userDto.getBirtDate(),DateUtil.PATTERN14));
            user.setRole(userDto.getRole() == Role.ADMIN ? Role.ADMIN : Role.USER);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return userRepository.save(user);
    }

}
