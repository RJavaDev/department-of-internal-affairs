package uz.internal_affairs.dto.citizen_cotegory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PROFCitizenDto extends BaseCitizenDto { //  PROF Hisobda turuvchi Shaxslar
    private Long regionId;                // mahalla id
    private String locationInformation;   // joy xaqida tuliq malumot
    private String regionAddress;
    private String standUpPROF;             // Tekshirilga PROF Hisobda turuvchi Shaxs
    private String employeeSummary;         //Hodim Xulosasi(text)

}
