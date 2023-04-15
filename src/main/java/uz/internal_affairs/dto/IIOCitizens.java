package uz.internal_affairs.dto;

import lombok.Data;

import java.io.File;
import java.util.Date;

@Data
public class IIOCitizens {
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birtDate;
    private String phoneNumber;
    private String city;
    private String region;
    private String address;
    private File  image;
    private File  fingerprint;
    private String causeOfEvent;
    private String EmployeeSummary;
    private String placeOfImport;
}
