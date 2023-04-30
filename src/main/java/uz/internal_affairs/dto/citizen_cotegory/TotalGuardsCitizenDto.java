package uz.internal_affairs.dto.citizen_cotegory;

import lombok.Getter;
import lombok.Setter;
import uz.internal_affairs.interfaces.CitizenInterface;

import java.util.ArrayList;
import java.util.List;

import static uz.internal_affairs.common.util.CategoryEnum.TOTAL_CHECKED_OBJECT_GUARDS;

@Getter
@Setter
public class TotalGuardsCitizenDto extends BaseCitizenDto{ // jami tekshirilgan obekt qarovullar
    private Long regionId;                    // mahalla id
    private String locationInformation;       // joy xaqida tuliq malumot
    private String regionAddress;
    private String locationInformationObject; // obekt manzili

    public static List totalGuardsCitizenDtoList(List<CitizenInterface> pageCitizens) {
        List<TotalGuardsCitizenDto> citizenList = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            TotalGuardsCitizenDto dto = new TotalGuardsCitizenDto();
            dto.setId(citizen.getId());
            dto.setFirstName(citizen.getFirst_name());
            dto.setLastName(citizen.getLast_name());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setPhoneNumber(citizen.getPhone_number());
            dto.setCategoryId(citizen.getCategory_id());
            dto.setRegionId(citizen.getRegion_id());
            dto.setStatus(citizen.getStatus());
            dto.setCategory(TOTAL_CHECKED_OBJECT_GUARDS.name());
            dto.setLocationInformation(citizen.getLocation_information());
            dto.setRegionAddress(citizen.getCitizen_address());
            dto.setLocationInformationObject(citizen.getLocation_information_object());
            citizenList.add(dto);
        }
        return citizenList;
    }

}
