package uz.internal_affairs.dto.citizen_cotegory;

import lombok.*;
import uz.internal_affairs.dto.FileDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class IIOCitizensDto extends BaseCitizenDto {
    private String birthDate;             // pattern = dd.mm.yyyy
    private Long regionId;                // mahalla id
    private String locationInformation;   // joy xaqida tuliq malumot
    private String regionAddress;
    private FileDto fingerprintFile;      // barmoq izi
    private FileDto imageDto;             // rasm
    private String imageUrl;
    private String fingerprintUrl;
    private String causeOfEvent;          // Voqea sababi
    private String employeeSummary;       // hodim Xulosasi
    private String placeOfImport;         // olib kelingan joy


}
