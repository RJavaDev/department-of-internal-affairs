package uz.internal_affairs.dto;

import lombok.Getter;
import lombok.Setter;
import uz.internal_affairs.dto.base.BaseServerModifierDto;

@Getter
@Setter
public class FileDto extends BaseServerModifierDto {
    private String path;                // stored in disk place
    private String name;                // encoded uuid
    private String originalName;        // original file-name
}
