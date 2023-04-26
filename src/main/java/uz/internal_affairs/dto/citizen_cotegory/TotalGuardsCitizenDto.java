package uz.internal_affairs.dto.citizen_cotegory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalGuardsCitizenDto extends BaseCitizenDto{ // jami tekshirilgan obekt qarovullar
    private Long regionId;                    // mahalla id
    private String locationInformation;       // joy xaqida tuliq malumot
    private String regionAddress;
    private String locationInformationObject; // obekt manzili

}
