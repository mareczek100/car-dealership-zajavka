package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Builder
@EqualsAndHashCode(of = "serialNumber")
@ToString(exclude = "carServiceParts")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "part")
public class PartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Integer partId;
    @Column(name = "serial_number", unique = true)
    private String serialNumber;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "part")
    private Set<CarServicePartsEntity> carServiceParts;

}
