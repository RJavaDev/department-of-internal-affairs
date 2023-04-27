package uz.internal_affairs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.internal_affairs.common.util.SecurityUtils;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.dto.UserScoreDto;
import uz.internal_affairs.dto.citizen_cotegory.AllCitizenDto;
import uz.internal_affairs.dto.citizen_cotegory.IIOCitizensDto;
import uz.internal_affairs.dto.response.DataGrid;
import uz.internal_affairs.dto.response.FilterForm;
import uz.internal_affairs.dto.response.HttpResponse;
import uz.internal_affairs.entity.CitizenEntity;
import uz.internal_affairs.entity.UserEntity;
import uz.internal_affairs.repository.UserRepository;
import uz.internal_affairs.sevice.CategoryService;
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
    private final UserRepository userRepository;

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

            response.code(HttpResponse.Status.OK).success(true).body(userService.getUserAll()).message("successfully!!!");
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
                return response.code(HttpResponse.Status.OK).body(true).success(true);
            }
        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Operation(summary = "This method for get", description = "This method way to determine what the user has job done")
    /**
     * admin tomondan userni bir oylig qigan ishini kuradi kuradi
     **/
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user-work-done/{id}")
    public HttpResponse<Object> getUserWorkDone(@PathVariable Long id, HttpServletRequest request, @RequestBody FilterForm filterForm) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            List<AllCitizenDto> workDone = citizenService.getWorkDone(id, request, filterForm);
            response.code(HttpResponse.Status.OK).success(true).body(workDone).message("successfully!!!");
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    /**
     * user uzini bir oylig qilgan ishlarini kuradi
     **/
    @GetMapping("/my-work-done")
    public HttpResponse<Object> getWorkDone(HttpServletRequest request, @RequestBody FilterForm filterForm) {
        HttpResponse<Object> response = new HttpResponse<>(true);
        try {
            Long currentUserId = userRepository.findByUsername(SecurityUtils.getUsername()).orElseThrow().getId();
            DataGrid<AllCitizenDto> allCitizenDtoDataGrid = citizenService.userJobs(currentUserId, request, filterForm);
            response.code(HttpResponse.Status.OK).success(true).body(allCitizenDtoDataGrid).message("successfully!!!");
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
