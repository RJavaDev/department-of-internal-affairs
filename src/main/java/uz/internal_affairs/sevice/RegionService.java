package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.internal_affairs.dto.RegionDto;
import uz.internal_affairs.entity.RegionEntity;
import uz.internal_affairs.repository.RegionRepository;

@Service
@RequiredArgsConstructor
public class RegionService implements BaseService<RegionDto, RegionEntity>{

    private final RegionRepository regionRepository;
    @Override
    public RegionEntity add(RegionDto regionDTO) {
        RegionEntity regionEntity = RegionEntity.builder()
//                .regionName(regionDTO.getRegionName())
                .build();
        return regionRepository.save(regionEntity);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public RegionEntity update(Long id, RegionDto regionDTO) {
        return null;
    }
}
