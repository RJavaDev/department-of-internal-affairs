package uz.internal_affairs.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CitizenDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birtDate;
    private String phoneNumber;
    private String locationInformation;//joyi haqidsa
    private String placeOfImport;//olib kelingan joy
    private String causeOfEvent;//voqiya sababi(text)
    private String EmployeeSummary; //Hodim Xulosasi(text)
    private String statement;//  tuzilgan bayonat (text)
    private String huntingWeaponModel;// qurol madeli
    private String huntingWeaponCode;// qurol raqami
    private String categoryId;
    private String fingerprintPicture;
    private Integer userid;
}
