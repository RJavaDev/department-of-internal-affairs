package uz.internal_affairs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import uz.internal_affairs.constants.TableNames;
import uz.internal_affairs.dto.base.citizen.BaseCitizenDto;
import uz.internal_affairs.entity.base.BaseCitizenEntity;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.DEPARTMENT_CITIZEN)
public class CitizenEntity extends BaseCitizenEntity {

    private Date birtDate;

    private String locationInformation;//joyi haqida

    private String placeOfImport;//olib kelingan joy

    @Column(columnDefinition="TEXT")
    private String causeOfEvent;//voqiya sababi(text)

    @Column(columnDefinition="TEXT")
    private String employeeSummary; //Hodim Xulosasi(text)

    @Column(columnDefinition="TEXT")
    private String statement;//  tuzilgan bayonat (text)

    private String huntingWeaponModel;// qurol madeli

    private String huntingWeaponCode;// qurol raqami

    @Column(name = "categoryId", nullable = false)
    private Long categoryId;
    @ManyToOne
    @JoinColumn(name = "categoryId", insertable = false, updatable = false)
    private CategoryEntity categoryEntity;

    @OneToOne
    @JoinColumn(name = "image_id")
    private FileEntity imageFile;
    @OneToOne
    @JoinColumn(name = "fingerprint_id")
    private FileEntity fingerprintFile;

    @Column(name = "userId", nullable = false)
    private Long userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private UserEntity userEntity;

    public <T extends BaseCitizenDto> T getDto(T dto){
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
