package uz.internal_affairs.dto.citizen_cotegory;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import uz.internal_affairs.interfaces.CitizenInterface;

import java.util.ArrayList;
import java.util.List;

import static uz.internal_affairs.common.util.CategoryEnum.CHECKED_HUNTING_WEAPONS;

@Getter
@Setter
public class HuntingWeaponsCitizenDto extends BaseCitizenDto{ // tekshirilgan ov qurollar
    private String huntingWeaponModel;     // qurol madeli
    private String huntingWeaponCode;       // qurol raqami
    private String employeeSummary;         //Hodim Xulosasi(text)


    public static List<HuntingWeaponsCitizenDto> huntingWeaponsCitizenDtoList(List<CitizenInterface> pageCitizens) {
        List<HuntingWeaponsCitizenDto> citizenList = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            HuntingWeaponsCitizenDto dto = new HuntingWeaponsCitizenDto();
            dto.setId(citizen.getId());
            dto.setFirstName(citizen.getFirst_name());
            dto.setLastName(citizen.getLast_name());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setPhoneNumber(citizen.getPhone_number());
            dto.setCategoryId(citizen.getCategory_id());
            dto.setStatus(citizen.getStatus());
            dto.setCategory(CHECKED_HUNTING_WEAPONS.name());
            dto.setHuntingWeaponModel(citizen.getHunting_weapon_model());
            dto.setHuntingWeaponCode(citizen.getHunting_weapon_code());
            dto.setEmployeeSummary(citizen.getEmployee_summary());
            citizenList.add(dto);
        }
        return citizenList;
    }

}