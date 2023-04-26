package uz.internal_affairs.dto.citizen_cotegory;

import lombok.*;
import uz.internal_affairs.dto.FileDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaughtLostCitizenDto extends BaseCitizenDto{ // Aniqlangan bedaraklar

    private Long regionId;                // mahalla id
    private String locationInformation;   // joy xaqida tuliq malumot
    private String regionAddress;
    private FileDto fingerprintFile;      // barmoq izi
    private FileDto imageDto;             // rasm
    private String imageUrl;
    private String fingerprintUrl;


    private String employeeSummary;       // hodim Xulosasi


}
