package uz.internal_affairs.dto.citizen_cotegory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PROFCitizenDto extends BaseCitizenDto { //  PROF Hisobda turuvchi Shaxslar
    private Long regionId;                // mahalla id
    private String locationInformation;   // joy xaqida tuliq malumot
    private String region_name;
    private String neighborhood_name;
    private String standUpPROF;             // Tekshirilga PROF Hisobda turuvchi Shaxs
    private String employeeSummary;         //Hodim Xulosasi(text)

}
