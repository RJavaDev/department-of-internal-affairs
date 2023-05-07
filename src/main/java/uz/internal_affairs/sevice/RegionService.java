package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import uz.internal_affairs.dto.RegionDto;
import uz.internal_affairs.entity.RegionEntity;
import uz.internal_affairs.repository.RegionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("regionService")
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;


    public Object regionTree(Long regionId){
        List<RegionEntity> parentRegions = regionRepository.findAllByParentId(regionId);
        return parentRegions.stream().map(RegionEntity::getDto).collect(Collectors.toList());
    }

    public List<RegionDto> regionList(){
        List<RegionEntity> allRegions = regionRepository.allValidRegions();
        log.atInfo().log("!Получение... Все регионов");
        return allRegions.stream().map(r -> r.getDto(false)).collect(Collectors.toList());
    }

    public RegionDto getRegionByName(String regionName){
        if(StringUtils.isEmpty(regionName)) return null;
        Optional<RegionEntity> optRegion = regionRepository.findByName(regionName);
        RegionDto dto = null;
        if(optRegion.isPresent()) dto = optRegion.get().getDto();
        return dto;
    }

    public RegionDto getRegionById(Long regionId){
        if(regionId == null) return null;
        Optional<RegionEntity> optRegion = regionRepository.findById(regionId);
        RegionDto result = null;
        if(optRegion.isPresent()) result = optRegion.get().getDto();
        return result;
    }
}
