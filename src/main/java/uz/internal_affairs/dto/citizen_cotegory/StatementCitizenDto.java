package uz.internal_affairs.dto.citizen_cotegory;

import lombok.Getter;
import lombok.Setter;
import uz.internal_affairs.dto.FileDto;
import uz.internal_affairs.interfaces.CitizenInterface;

import java.util.ArrayList;
import java.util.List;

import static uz.internal_affairs.common.util.CategoryEnum.STATEMENT;

@Getter
@Setter
public class StatementCitizenDto extends BaseCitizenDto{  //bayonat
    private Long regionId;                // mahalla id
    private String locationInformation;   // joy xaqida tuliq malumot
    private String regionAddress;
    private String birthDate;             // pattern = dd.mm.yyyy
    private FileDto fingerprintFile;      // barmoq izi
    private FileDto imageDto;             // rasm
    private String imageUrl;
    private String fingerprintUrl;
    private String employeeSummary;         //Hodim Xulosasi(text)
    private String statement;              //  tuzilgan bayonat (text)
    private String placeOfImport;           //olib kelingan joy

    public static List<StatementCitizenDto> statementCitizenDtoList(List<CitizenInterface> pageCitizens) {
        List<StatementCitizenDto> citizenDtoList = new ArrayList<>();
        for (CitizenInterface citizen : pageCitizens) {
            StatementCitizenDto dto = new StatementCitizenDto();
            dto.setRegionId(citizen.getId());
            dto.setLastName(citizen.getLast_name());
            dto.setFirstName(citizen.getFirst_name());
            dto.setMiddleName(citizen.getMiddle_name());
            dto.setPhoneNumber(citizen.getPhone_number());
            dto.setCategory(STATEMENT.name());
            dto.setStatus(citizen.getStatus());
            dto.setBirthDate(citizen.getBirth_date().toString());
            dto.setLocationInformation(citizen.getLocation_information());
            dto.setRegionAddress(citizen.getLocation_information());
            dto.setEmployeeSummary(citizen.getEmployee_summary());
            dto.setStatement(citizen.getStatement());
            dto.setRegionAddress(citizen.getCitizen_address());
            dto.setPlaceOfImport(citizen.getPlace_of_import());
            citizenDtoList.add(dto);
        }
        return citizenDtoList;
    }
}
