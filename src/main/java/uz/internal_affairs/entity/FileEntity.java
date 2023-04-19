package uz.internal_affairs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.internal_affairs.constants.TableNames;
import uz.internal_affairs.dto.FileDto;
import uz.internal_affairs.dto.UserDto;
import uz.internal_affairs.entity.base.BaseServerModifierEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.DEPARTMENT_FILE)
public class FileEntity extends BaseServerModifierEntity {

    private String path;                // stored in disk place
    @Column(unique = true)
    private String name;                // encoded uuid
    private String originalName;        // original file-name


    /************************************************************
     * ******************** CONVERT TO DTO ***********************
     * ***********************************************************/
    public FileDto toDto(){
        return toDto(this, new FileDto());
    }
}
