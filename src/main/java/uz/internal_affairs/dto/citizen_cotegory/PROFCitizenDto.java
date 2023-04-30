package uz.internal_affairs.dto.citizen_cotegory;

import lombok.Getter;
import lombok.Setter;
import uz.internal_affairs.interfaces.CitizenInterface;

import java.util.ArrayList;
import java.util.List;

import static uz.internal_affairs.common.util.CategoryEnum.PROF_CITIZEN;

@Getter
@Setter
public class PROFCitizenDto extends BaseCitizenDto { //  PROF Hisobda turuvchi Shaxslar
    private Long regionId;                // mahalla id
    private String locationInformation;   // joy xaqida tuliq malumot
    private String regionAddress;
    private String standUpPROF;             // Tekshirilga PROF Hisobda turuvchi Shaxs
    private String employeeSummary;         //Hodim Xulosasi(text)

    public static List PROFCitizenDtoList(List<CitizenInterface> pageCitizens) {
        List<PROFCitizenDto> profCitizenDtoList = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            PROFCitizenDto dto = new PROFCitizenDto();
            dto.setLastName(citizen.getLast_name());
            dto.setFirstName(citizen.getFirst_name());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setId(citizen.getId());
            dto.setStatus(citizen.getStatus());
            dto.setCategory(PROF_CITIZEN.name());
            dto.setCategoryId(citizen.getCategory_id());
            dto.setCreatedDate(citizen.getCreated_date());
            dto.setLocationInformation(citizen.getLocation_information());
            dto.setRegionAddress(citizen.getCitizen_address());
            dto.setStandUpPROF(citizen.getStand_upprof());
            dto.setEmployeeSummary(citizen.getEmployee_summary());
            dto.setPhoneNumber(citizen.getPhone_number());
            profCitizenDtoList.add(dto);
        }
        return profCitizenDtoList;
    }

}
