package uz.internal_affairs.interfaces;

import uz.internal_affairs.constants.EntityStatus;

import java.time.LocalDateTime;
import java.util.Date;

public interface CitizenInterface {
    Long getId();

    EntityStatus getStatus();

    Long getCreated_by();

    LocalDateTime getCreated_date();

    Long getModified_by();

    Date getUpdated_date();

    String getFirst_name();

    String getLast_name();

    String getMiddle_name();

    String getPhone_number();

    Date getBirth_date();

    Long getCategory_id();

    String getCause_of_event();

    String getEmployee_summary();

    String getHunting_weapon_code();

    String getHunting_weapon_model();

    String getLocation_information();
    String getStand_upprof();
    String getLocation_information_object();


    String getPlace_of_import();

    Long getRegion_id();

    String getStatement();

    Long getUser_id();

    Long getFingerprint_id();

    Long getImage_id();

    String getCitizen_address();
}
