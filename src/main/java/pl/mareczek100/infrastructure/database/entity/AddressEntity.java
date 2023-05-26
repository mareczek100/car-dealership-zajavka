package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Getter
@ToString(exclude = "customers")
@Entity
@EqualsAndHashCode(of = {"country", "city", "postalCode", "street", "buildingFlatNumber"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
@Builder
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "street")
    private String street;
    @Column(name = "building_flat_number")
    private String buildingFlatNumber;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    private Set<CustomerEntity> customers;


}
