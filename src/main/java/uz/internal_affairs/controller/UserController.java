package uz.internal_affairs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.internal_affairs.common.util.SecurityUtils;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.dto.UserScoreDto;
import uz.internal_affairs.dto.citizen_cotegory.IIOCitizensDto;
import uz.internal_affairs.dto.response.HttpResponse;
import uz.internal_affairs.sevice.CitizenService;
import uz.internal_affairs.sevice.UserService;

import java.util.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "This Controller for user")
public class UserController {

    private final UserService userService;
    private final CitizenService citizenService;

    @GetMapping("/get-user-score")
    @Operation(summary = "Method of user score",
            description = "points accumulated by the current user for one day and one month")
    public HttpResponse<Object> getUserScore() {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            UserScoreDto userScore = userService.getUserScore();
            response.code(HttpResponse.Status.OK).success(true).body(userScore).message("successfully!!!");
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This method for get",description = "To get all users for admin")
    @GetMapping("/list")
    public HttpResponse<Object> getUserList() {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            List<UserDto> userAll = userService.getUserAll();
            response.code(HttpResponse.Status.OK).success(true).body(userAll).message("successfully!!!");
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This method for get", description = "This method is used to get how many points the admin user has scored")
    @GetMapping("/info/{id}")
    public HttpResponse<Object> getUserInformation(@PathVariable Long id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            UserDto userDto = userService.getUserInformation(id);
            response.code(HttpResponse.Status.OK).success(true).body(userDto).message("successfully!!!");
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Operation(summary = "This method for update", description = "This method updates the user's data")
    @PostMapping("/update")
    public HttpResponse<Object> userUpdate(@RequestBody UserDto userDto) {
        HttpResponse<Object> response = new HttpResponse<>(true);

        try {
            if (userService.updateUser(userDto)) {
                return response.code(HttpResponse.Status.OK).success(true);
            }
        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Operation(summary = "This method for get", description = "This method way to determine what the user has job done")
    @GetMapping("/my-work-done")
    public HttpResponse<Object> getWorkDone() {
        HttpResponse<Object> response = new HttpResponse<>(true);
        try {
            String username = SecurityUtils.getUsername();
            List<IIOCitizensDto> workDone = citizenService.getWorkDone(username);
            response.code(HttpResponse.Status.OK).success(true).body(workDone).message("successfully!!!");
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Operation(summary = "This user for update", description = "This method is designed to delete a user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> userDelete(@PathVariable Long id) {

        HttpResponse<Object> response = new HttpResponse<>(true);

        try {
            if (userService.userDelete(id)) {
                return response.code(HttpResponse.Status.OK).success(true).body(Boolean.TRUE).message("User deleted successfully");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


}
