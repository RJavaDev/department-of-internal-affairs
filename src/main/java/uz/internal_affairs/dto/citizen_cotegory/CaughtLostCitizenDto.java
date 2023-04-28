package uz.internal_affairs.dto.citizen_cotegory;

import lombok.Getter;
import lombok.Setter;
import uz.internal_affairs.dto.FileDto;
import uz.internal_affairs.interfaces.CitizenInterface;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CaughtLostCitizenDto extends BaseCitizenDto{ // Aniqlangan bedaraklar

    private Long regionId;                // mahalla id
    private String locationInformation;   // joy xaqida tuliq malumot
    private String regionAddress;
    private FileDto fingerprintFile;      // barmoq izi
    private FileDto imageDto;             // rasm
    private String imageUrl;
    private String fingerprintUrl;


    private String employeeSummary;       // hodim Xulosasi

    public static List<CaughtLostCitizenDto> caughtLostCitizenList(List<CitizenInterface> pageCitizens) {
        List<CaughtLostCitizenDto> citizenList = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            CaughtLostCitizenDto dto = new CaughtLostCitizenDto();
            dto.setId(citizen.getId());
            dto.setFirstName(citizen.getFirst_name());
            dto.setLastName(citizen.getLast_name());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setPhoneNumber(citizen.getPhone_number());
            dto.setCategoryId(citizen.getCategory_id());
            dto.setRegionId(citizen.getRegion_id());
            dto.setStatus(citizen.getStatus());
            dto.setLocationInformation(citizen.getLocation_information());
            dto.setEmployeeSummary(citizen.getEmployee_summary());
            citizenList.add(dto);
        }
        return citizenList;
    }
}
