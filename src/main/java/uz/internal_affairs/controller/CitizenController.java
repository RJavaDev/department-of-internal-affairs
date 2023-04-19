package uz.internal_affairs.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.internal_affairs.common.util.SecurityUtils;
import uz.internal_affairs.dto.citizen_cotegory.IIOCitizensDto;
import uz.internal_affairs.dto.response.DataGrid;
import uz.internal_affairs.dto.response.FilterForm;
import uz.internal_affairs.dto.response.HttpResponse;
import uz.internal_affairs.sevice.CitizenService;

@RestController
@RequestMapping("/api/v1/citizen")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

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

    @PostMapping("/data")
    public HttpResponse<Object> dataGrid(HttpServletRequest request, @RequestBody FilterForm filter){
        HttpResponse<Object> response = HttpResponse.build(false);
        try{
            DataGrid<IIOCitizensDto> datagrid = citizenService.dataGrid(request, filter);
            response.code(HttpResponse.Status.OK).success(true).body(datagrid);
        }
        catch (Exception e){
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

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

    @GetMapping("/test")
    public String getTest(){
        return SecurityUtils.getUsername();
    }
}
