package uz.internal_affairs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.internal_affairs.dto.RegionDTO;
import uz.internal_affairs.entity.Region;
import uz.internal_affairs.sevice.RegionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/region")
public class RegionController {
    private final RegionService regionService;

    @PostMapping("/add")
    public ResponseEntity<Region> add(RegionDTO regionDTO){
        return ResponseEntity.ok(regionService.add(regionDTO));
    }
}
