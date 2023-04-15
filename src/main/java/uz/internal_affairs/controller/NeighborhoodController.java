package uz.internal_affairs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.internal_affairs.dto.NeighborhoodDTO;
import uz.internal_affairs.entity.Neighborhood;
import uz.internal_affairs.sevice.NeighborhoodService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/neighborhood")
public class NeighborhoodController {

    private final NeighborhoodService neighborhoodService;

    @PostMapping("/add")
    public ResponseEntity<Neighborhood> add(@RequestBody NeighborhoodDTO neighborhoodDTO) {
        return ResponseEntity.ok(neighborhoodService.add(neighborhoodDTO));
    }
    @GetMapping("/list")
    public ResponseEntity<List<Neighborhood>>getRegionList(){
        return ResponseEntity.ok(neighborhoodService.getNeighborhoodRepository());
    }
}
