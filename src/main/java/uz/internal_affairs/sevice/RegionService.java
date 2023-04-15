package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.internal_affairs.dto.RegionDTO;
import uz.internal_affairs.entity.Region;
import uz.internal_affairs.repository.RegionRepository;

@Service
@RequiredArgsConstructor
public class RegionService implements BaseService<RegionDTO, Region>{

    private final RegionRepository regionRepository;
    @Override
    public Region add(RegionDTO regionDTO) {
        Region region = Region.builder()
                .regionName(regionDTO.getRegionName())
                .build();
        return regionRepository.save(region);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Region update(Long id, RegionDTO regionDTO) {
        return null;
    }
}
