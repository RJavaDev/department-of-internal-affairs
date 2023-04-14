package uz.internal_affairs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FingerprintPicture extends Base {

    private String path;
    @Column(unique = true)
    private String name;
    private String originalName;
}
