package uz.internal_affairs.dto.citizen_cotegory;

import lombok.*;
import uz.internal_affairs.dto.FileDto;
import uz.internal_affairs.dto.base.citizen.BaseCitizenDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IIOCitizensDto extends BaseCitizenDto {
    private String birtDate;            // pattern = dd.mm.yyyy

    private Long parentRegionId;

    private String parentRegionName;            // tuman

    private Long childRegionId;

    private String childRegionName;         // mahalla

    private String locationInformation;     // joy haqida

    private FileDto fingerprintFile;

    private FileDto imageDto;

    private String  imageUrl;

    private String  fingerprintUrl;

    private String causeOfEvent;

    private String employeeSummary;

    private String placeOfImport;
}
