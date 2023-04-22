package uz.internal_affairs.dto.citizen_cotegory;

import lombok.*;
import uz.internal_affairs.dto.FileDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IIOCitizensDto extends BaseCitizenDto {
    private String birthDate;            // pattern = dd.mm.yyyy

    private Long regionId;

    private String locationInformation;     // joy haqida

    private FileDto fingerprintFile;

    private FileDto imageDto;

    private String  imageUrl;

    private String  fingerprintUrl;

    private String causeOfEvent;

    private String employeeSummary;

    private String placeOfImport;

    private String regionAddress;

}
