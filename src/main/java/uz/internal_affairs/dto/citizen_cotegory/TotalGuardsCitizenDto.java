package uz.internal_affairs.dto.citizen_cotegory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalGuardsCitizenDto extends BaseCitizenDto{ // jami tekshirilgan obekt qarovullar
    private Long regionId;                    // mahalla id
    private String locationInformation;       // joy xaqida tuliq malumot
    private String regionAddress;
    private String locationInformationObject; // obekt manzili

}
