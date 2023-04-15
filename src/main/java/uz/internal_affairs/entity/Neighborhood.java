package uz.internal_affairs.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Neighborhood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String regionName;
    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region attach;

//    @ManyToOne
//    private Region region;
}
