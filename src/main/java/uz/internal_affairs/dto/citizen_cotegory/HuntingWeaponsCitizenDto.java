package uz.internal_affairs.dto.citizen_cotegory;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HuntingWeaponsCitizenDto extends BaseCitizenDto{ // tekshirilgan ov qurollar
    private String huntingWeaponModel;     // qurol madeli
    private String huntingWeaponCode;       // qurol raqami
    private String employeeSummary;         //Hodim Xulosasi(text)


}
