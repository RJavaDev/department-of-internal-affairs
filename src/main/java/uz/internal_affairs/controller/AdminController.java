package uz.internal_affairs.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.internal_affairs.entity.UserEntity;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping( "/user/list")
    public ResponseEntity<UserEntity> getUserList(){
        return null;
    }

}
