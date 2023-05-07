package uz.internal_affairs.sevice;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    public Object regionTree(Long regionId){
        List<RegionEntity> parentRegions = regionRepository.findAllByParentId(regionId);
        log.atInfo().log("!Получение... Дерево регионов");
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
        log.atInfo().log("!Получение... Регион по названию");
        RegionDto dto = null;
        if(optRegion.isPresent()) dto = optRegion.get().getDto(false);
        return dto;
    }

    public RegionDto getRegionById(Long regionId){
        if(regionId == null) return null;
        Optional<RegionEntity> optRegion = regionRepository.findById(regionId);
        log.atInfo().log("!Получение... Регион по ИД");
        RegionDto result = null;
        if(optRegion.isPresent()) result = optRegion.get().getDto(false);
        return result;
    }
}
