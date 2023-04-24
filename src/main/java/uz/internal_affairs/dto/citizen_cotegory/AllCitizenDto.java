package uz.internal_affairs.dto.citizen_cotegory;

import lombok.Getter;
import lombok.Setter;
import uz.internal_affairs.dto.FileDto;

@Getter
@Setter
public class AllCitizenDto extends BaseCitizenDto {
    private Long regionId;                     // mahalla id
    private String locationInformation;        // joy xaqida tuliq malumot
    private String regionAddress;
    private String birthDate;                 // tug'lgan sanasi pattern = dd.mm.yyyy
    private String standUpPROF;               // Tekshirilga PROF Hisobda turuvchi Shaxs
    private String locationInformationObject; // obekt manzili
    private String placeOfImport;             //olib kelingan joy
    private String causeOfEvent;              //voqiya sababi(text)
    private String employeeSummary;           //Hodim Xulosasi(text)
    private String statement;                 //  tuzilgan bayonat (text)
    private String huntingWeaponModel;        // qurol madeli
    private String huntingWeaponCode;         // qurol raqami
    private FileDto fingerprintFile;      // barmoq izi
    private FileDto imageDto;             // rasm
    private String imageUrl;
    private String fingerprintUrl;


}
