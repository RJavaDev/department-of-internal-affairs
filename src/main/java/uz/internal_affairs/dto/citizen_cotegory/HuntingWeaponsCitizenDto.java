package uz.internal_affairs.dto.citizen_cotegory;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HuntingWeaponsCitizenDto extends BaseCitizenDto{ // tekshirilgan ov qurollar
    private String huntingWeaponModel;     // qurol madeli
    private String huntingWeaponCode;       // qurol raqami
    private String employeeSummary;         //Hodim Xulosasi(text)


}
