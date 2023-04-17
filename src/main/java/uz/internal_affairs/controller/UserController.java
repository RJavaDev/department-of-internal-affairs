package uz.internal_affairs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.internal_affairs.dto.UserScoreDto;
import uz.internal_affairs.dto.response.HttpResponse;
import uz.internal_affairs.sevice.UserService;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-user-score")
    public HttpResponse<Object> getUserScore(){
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
           UserScoreDto userScore = userService.getUserScore();
            response.code(HttpResponse.Status.OK).success(true).body(userScore).message("IIOCitizen saved successfully!!!");
        }catch (Exception e){
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
