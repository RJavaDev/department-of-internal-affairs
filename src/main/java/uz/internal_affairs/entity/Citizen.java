package uz.internal_affairs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Citizen extends Base {

    private String firstName;
    private String lastName;
    private String middleName;
    private Date birtDate;
    private String phoneNumber;
    private String locationInformation;//joyi haqidsa
    private String placeOfImport;//olib kelingan joy
    @Column(columnDefinition="TEXT")
    private String causeOfEvent;//voqiya sababi(text)
    @Column(columnDefinition="TEXT")
    private String EmployeeSummary; //Hodim Xulosasi(text)
    @Column(columnDefinition="TEXT")
    private String statement;//  tuzilgan bayonat (text)
    private String huntingWeaponModel;// qurol madeli
    private String huntingWeaponCode;// qurol raqami
    @ManyToOne
    private Category category;
    @OneToOne
    private FingerprintImage fingerprintPicture;

    @ManyToOne
    private User user;


}
