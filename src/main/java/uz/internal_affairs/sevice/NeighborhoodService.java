package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.internal_affairs.dto.NeighborhoodDTO;
import uz.internal_affairs.entity.Neighborhood;
import uz.internal_affairs.repository.NeighborhoodRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class NeighborhoodService implements BaseService<NeighborhoodDTO,Neighborhood>{

    private final NeighborhoodRepository neighborhoodRepository;

    @Override
    public Neighborhood add(NeighborhoodDTO neighborhoodDTO) {
        Neighborhood neighborhood = Neighborhood.builder()
                .regionName(neighborhoodDTO.getNeighborhoodName())
                .regionId(neighborhoodDTO.getRegionId())

                .build();
       return neighborhoodRepository.save(neighborhood);
    }

    public List<Neighborhood> getNeighborhoodRepository() {
        return neighborhoodRepository.findAll();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Neighborhood update(Long id, NeighborhoodDTO neighborhoodDTO) {
        return null;
    }
}
