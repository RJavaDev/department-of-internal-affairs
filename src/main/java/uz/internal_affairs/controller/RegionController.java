package uz.internal_affairs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.internal_affairs.dto.RegionDto;
import uz.internal_affairs.entity.RegionEntity;
import uz.internal_affairs.sevice.RegionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/region")
public class RegionController {
    private final RegionService regionService;

    @PostMapping("/add")
    public ResponseEntity<RegionEntity> add(@RequestBody RegionDto regionDTO){
        return ResponseEntity.ok(regionService.add(regionDTO));
    }
}
