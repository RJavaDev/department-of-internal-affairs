package uz.internal_affairs.dto.citizen_cotegory;

import lombok.Getter;
import lombok.Setter;
import uz.internal_affairs.dto.FileDto;
import uz.internal_affairs.interfaces.CitizenInterface;

import java.util.ArrayList;
import java.util.List;

import static uz.internal_affairs.common.util.CategoryEnum.CAUGHT_WANTED_CITIZEN;

@Getter
@Setter
public class CaughtWantedCitizenDto extends BaseCitizenDto{  // Ushlangan qidiruvdagilar

    private Long regionId;                // mahalla id
    private String locationInformation;   // joy xaqida tuliq malumot
    private FileDto fingerprintFile;      // barmoq izi
    private FileDto imageDto;             // rasm
    private String imageUrl;
    private String fingerprintUrl;
    private String employeeSummary;       // hodim Xulosasi
    private String regionAddress;

    public static List caughtWantedCitizenDtoList(List<CitizenInterface> pageCitizens) {
        List<CaughtWantedCitizenDto> caughtWantedCitizenDtoList = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            CaughtWantedCitizenDto dto = new CaughtWantedCitizenDto();
            dto.setId(citizen.getId());
            dto.setFirstName(citizen.getFirst_name());
            dto.setLastName(citizen.getLast_name());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setPhoneNumber(citizen.getPhone_number());
            dto.setCategoryId(citizen.getCategory_id());
            dto.setCategory(CAUGHT_WANTED_CITIZEN.name());
            dto.setStatus(citizen.getStatus());
            dto.setRegionId(citizen.getRegion_id());
            dto.setRegionAddress(citizen.getCitizen_address());
            dto.setLocationInformation(citizen.getLocation_information());
            dto.setEmployeeSummary(citizen.getEmployee_summary());
            caughtWantedCitizenDtoList.add(dto);
        }
        return caughtWantedCitizenDtoList;
    }



}
