package uz.internal_affairs.dto.citizen_cotegory;

import lombok.Getter;
import lombok.Setter;
import uz.internal_affairs.common.util.DateUtil;
import uz.internal_affairs.dto.FileDto;
import uz.internal_affairs.interfaces.CitizenInterface;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AllCitizenDto extends BaseCitizenDto {
    private Long regionId;                     // mahalla id
    private String locationInformation;        // joy xaqida tuliq malumot
    private String regionAddress;
    private String birthDate;                 // tug'lgan sanasi pattern = dd.mm.yyyy
    private String standUpPROF;               // Tekshirilga PROF Hisobda turuvchi Shaxs
    private String locationInformationObject; // obekt manzili
    private String placeOfImport;             //olib kelingan joy
    private String causeOfEvent;              //voqiya sababi(text)
    private String employeeSummary;           //Hodim Xulosasi(text)
    private String statement;                 //  tuzilgan bayonat (text)
    private String huntingWeaponModel;        // qurol madeli
    private String huntingWeaponCode;         // qurol raqami
    private FileDto fingerprintFile;      // barmoq izi
    private FileDto imageDto;             // rasm
    private String imageUrl;
    private String fingerprintUrl;


    public static List<AllCitizenDto> allCitizenDtoList(List<CitizenInterface> pageCitizens) {
        List<AllCitizenDto> list = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            AllCitizenDto dto = new AllCitizenDto();
            dto.setId(citizen.getId());
            dto.setCreatedBy(citizen.getCreated_by());
            dto.setCategoryId(citizen.getCategory_id());
            dto.setPhoneNumber(citizen.getPhone_number());
            dto.setFirstName(citizen.getFirst_name());
            dto.setLastName(citizen.getLast_name());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setStatus(citizen.getStatus());
            dto.setBirthDate(DateUtil.format(citizen.getBirth_date(), DateUtil.PATTERN14));
            dto.setRegionAddress(citizen.getCitizen_address());
            dto.setRegionId(citizen.getRegion_id());
            dto.setLocationInformation(citizen.getLocation_information());
            dto.setCauseOfEvent(citizen.getCause_of_event());
            dto.setEmployeeSummary(citizen.getEmployee_summary());
            dto.setPlaceOfImport(citizen.getPlace_of_import());
            list.add(dto);
        }
        return list;
    }

}
