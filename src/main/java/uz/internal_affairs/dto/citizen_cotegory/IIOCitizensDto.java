package uz.internal_affairs.dto.citizen_cotegory;

import lombok.*;
import uz.internal_affairs.common.util.DateUtil;
import uz.internal_affairs.dto.FileDto;
import uz.internal_affairs.interfaces.CitizenInterface;

import java.util.ArrayList;
import java.util.List;

import static uz.internal_affairs.common.util.CategoryEnum.IIO_CITIZEN;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IIOCitizensDto extends BaseCitizenDto {
    private String birthDate;             // pattern = dd.mm.yyyy
    private Long regionId;                // mahalla id
    private String locationInformation;   // joy xaqida tuliq malumot
    private String regionAddress;
    private FileDto fingerprintFile;      // barmoq izi
    private FileDto imageDto;             // rasm
    private String imageUrl;
    private String fingerprintUrl;
    private String causeOfEvent;          // Voqea sababi
    private String employeeSummary;       // hodim Xulosasi
    private String placeOfImport;         // olib kelingan joy

    public static List<IIOCitizensDto> IIOCitizenDtoList(List<CitizenInterface> pageCitizens) {
        List<IIOCitizensDto> iIOCitizenList = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            IIOCitizensDto dto = new IIOCitizensDto();
            dto.setId(citizen.getId());
            dto.setCreatedBy(citizen.getCreated_by());
            dto.setCategoryId(citizen.getCategory_id());
            dto.setPhoneNumber(citizen.getPhone_number());
            dto.setFirstName(citizen.getFirst_name());
            dto.setLastName(citizen.getLast_name());
            dto.setBirthDate(DateUtil.format(citizen.getBirth_date(), DateUtil.PATTERN14));
            dto.setCategory(IIO_CITIZEN.name());
            dto.setStatus(citizen.getStatus());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setRegionAddress(citizen.getCitizen_address());
            dto.setRegionId(citizen.getRegion_id());
            dto.setLocationInformation(citizen.getLocation_information());
            dto.setCauseOfEvent(citizen.getCause_of_event());
            dto.setEmployeeSummary(citizen.getEmployee_summary());
            dto.setPlaceOfImport(citizen.getPlace_of_import());
            iIOCitizenList.add(dto);
        }
        return iIOCitizenList;
    }

}
