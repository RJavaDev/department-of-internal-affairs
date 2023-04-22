package uz.internal_affairs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.internal_affairs.dto.RegionDto;
import uz.internal_affairs.dto.response.HttpResponse;
import uz.internal_affairs.sevice.RegionService;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping(path = {"/tree/{id}", "/tree"})
    public HttpResponse<Object> getRegionTreeById(@PathVariable(value = "id", required = false) Long regionId){
        return HttpResponse.build(true).code(HttpResponse.Status.OK).body(regionService.regionTree(regionId));
    }

    @GetMapping("/get/{id}")
    public HttpResponse<Object> getRegionById(@RequestParam("id") Long regionId){
        HttpResponse<Object> response = HttpResponse.build(false);
        try{
            RegionDto regionDto = regionService.getRegionById(regionId);
            if(regionDto == null) response.code(HttpResponse.Status.NOT_FOUND).success(false);
            else response.code(HttpResponse.Status.OK).success(true).body(regionDto);
        }
        catch(Exception ex){
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping("/{name}")
    public HttpResponse<Object> getRegionById(@PathVariable(value = "name", required = false) String regionName){
        HttpResponse<Object> response = HttpResponse.build(false);
        try{
            RegionDto regionDto = regionService.getRegionByName(regionName);
            if(regionDto == null) response.code(HttpResponse.Status.NOT_FOUND).success(false);
            else response.code(HttpResponse.Status.OK).success(true).body(regionDto);
        }
        catch(Exception ex){
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
