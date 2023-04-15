package uz.internal_affairs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.internal_affairs.dto.NeighborhoodDTO;
import uz.internal_affairs.entity.Neighborhood;
import uz.internal_affairs.sevice.NeighborhoodService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/neighborhood")
public class NeighborhoodController {

    private final NeighborhoodService neighborhoodService;

    @PostMapping("/add")
    public ResponseEntity<Neighborhood> add(NeighborhoodDTO neighborhoodDTO) {
        return ResponseEntity.ok(neighborhoodService.add(neighborhoodDTO));
    }
}
