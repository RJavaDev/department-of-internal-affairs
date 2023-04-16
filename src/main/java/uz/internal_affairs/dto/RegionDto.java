package uz.internal_affairs.dto;

import lombok.Data;
import uz.internal_affairs.dto.base.BaseServerDto;

import java.util.*;

@Data
public class RegionDto extends BaseServerDto {
    private Long id;

    private List<RegionDto> children;
}
