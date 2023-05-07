package uz.internal_affairs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.internal_affairs.dto.RegionDto;
import uz.internal_affairs.dto.response.HttpResponse;
import uz.internal_affairs.sevice.RegionService;

@RestController
@RequestMapping("/api/v1/region")
@Tag(name = "Region controller", description = "This Controller for region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @Operation(summary = "This method for get", description = "This method serves to get the region in the form of a tree")
    @GetMapping(path = {"/tree/{id}", "/tree"})
    public HttpResponse<Object> getRegionTreeById(@PathVariable(value = "id", required = false) Long regionId) {
        return HttpResponse.build(true).code(HttpResponse.Status.OK).body(regionService.regionTree(regionId));
    }

    @GetMapping("/list")
    public HttpResponse<Object> getAllRegionList(){
        return HttpResponse.build(true).code(HttpResponse.Status.OK).body(regionService.regionList());
    }

    @Operation(summary = "This method for get", description = "This method is designed to get the region by id")
    @GetMapping("/get/{id}")
    public HttpResponse<Object> getRegionById(@PathVariable("id") Long regionId) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            RegionDto regionDto = regionService.getRegionById(regionId);
            if (regionDto == null) response.code(HttpResponse.Status.NOT_FOUND).success(false);
            else response.code(HttpResponse.Status.OK).success(true).body(regionDto);
        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Operation(summary = "This method for get", description = "This method is designed to get the region by name")
    @GetMapping("/{name}")
    public HttpResponse<Object> getRegionById(@PathVariable(value = "name", required = false) String regionName) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            RegionDto regionDto = regionService.getRegionByName(regionName);
            if (regionDto == null) response.code(HttpResponse.Status.NOT_FOUND).success(false);
            else response.code(HttpResponse.Status.OK).success(true).body(regionDto);
        } catch (Exception ex) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
