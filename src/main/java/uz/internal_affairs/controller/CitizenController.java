package uz.internal_affairs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.internal_affairs.dto.citizen_cotegory.IIOCitizensDto;
import uz.internal_affairs.dto.response.FilterForm;
import uz.internal_affairs.dto.response.HttpResponse;
import uz.internal_affairs.sevice.CitizenService;

@RestController
@RequestMapping("/api/v1/citizen")
@Tag(name = "Citizen Controller", description = "This Controller for citizen")
@RequiredArgsConstructor
public class CitizenController {

    private final CitizenService citizenService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Operation(summary = "Method for add post",description = "This method is for adding citizens")
    @PostMapping("/save")
    public HttpResponse<Object> saveCitizen(HttpServletRequest request, @RequestBody IIOCitizensDto dto){
        HttpResponse<Object> response = HttpResponse.build(false);
        try{
            IIOCitizensDto savedCitizen = citizenService.saveCitizen(request, dto);
            if(savedCitizen != null && savedCitizen.getId() != null){
                response.code(HttpResponse.Status.OK).success(true).body(savedCitizen).message("IIOCitizen saved successfully!!!");
            }
            else{
                response.code(HttpResponse.Status.BAD_REQUEST);
            }
        }
        catch(Exception e){
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Operation(summary = "Method for get post", description = "This method is designed to filter citizens by category\n")
    @PostMapping("/data")
    public HttpResponse<Object> dataGrid(HttpServletRequest request, @RequestBody FilterForm filter){
        HttpResponse<Object> response = HttpResponse.build(false);
        try{
            response.code(HttpResponse.Status.OK).success(true).body(citizenService.datagrid(request, filter));
        }
        catch (Exception e){
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Method for get post", description = "This method is for filtering citizens by category, entered time and place")
    @PostMapping("/list")
    public HttpResponse<Object> getCitizens(HttpServletRequest request, @RequestBody FilterForm filter){
        HttpResponse<Object> response = HttpResponse.build(false);
        try{
            response.code(HttpResponse.Status.OK).success(true).body(citizenService.citizenList(request, filter));
        }
        catch (Exception e){
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Operation(summary = "This method citizen delete", description = "This method is intended for deletion by citizen ID")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> delete(@PathVariable("id") Long id){
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(citizenService.deleteIIOCitizen(id));
        }catch (Exception e){
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
