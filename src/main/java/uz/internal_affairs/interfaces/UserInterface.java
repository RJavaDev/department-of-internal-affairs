package uz.internal_affairs.interfaces;

import uz.internal_affairs.constants.EntityStatus;
import uz.internal_affairs.entity.role.Role;

import java.util.Date;

public interface UserInterface {
    Long getId();

    EntityStatus getStatus();

    Long getCreated_by();

    Date getCreated_date();

    Long getModified_by();

    Date getUpdated_date();

    Date getBirth_date();

    String getFirstname();

    String getLastname();

    String getMiddlename();

    String getPassword();

    String getPhone_number();

    Role getRole();

    String getUsername();

    Long getImage_id();

    Long getNeighborhood_id();

    Long getRegion_id();

    Long getParent_region_id();

    String getRegion_name();

    String getParent_region_name();
}
