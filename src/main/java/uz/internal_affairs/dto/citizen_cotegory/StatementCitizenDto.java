package uz.internal_affairs.dto.citizen_cotegory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.internal_affairs.dto.FileDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatementCitizenDto extends BaseCitizenDto{  //bayonat
    private Long regionId;                // mahalla id
    private String locationInformation;   // joy xaqida tuliq malumot
    private String region_name;
    private String neighborhood_name;
    private String birthDate;             // pattern = dd.mm.yyyy
    private FileDto fingerprintFile;      // barmoq izi
    private FileDto imageDto;             // rasm
    private String imageUrl;
    private String fingerprintUrl;
    private String employeeSummary;         //Hodim Xulosasi(text)
    private String statement;              //  tuzilgan bayonat (text)
    private String placeOfImport;           //olib kelingan joy
}
