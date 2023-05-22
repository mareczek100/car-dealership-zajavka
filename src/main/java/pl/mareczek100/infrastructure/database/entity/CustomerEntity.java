package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Data
@Entity
@Builder
@ToString(exclude = {"addressEntity", "carServiceRequestEntities", "invoiceEntities"})
@EqualsAndHashCode(of = "email")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email", unique = true)
    private String email;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity addressEntity;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Fetch(FetchMode.JOIN)
    private Set<CarServiceRequestEntity> carServiceRequestEntities;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<InvoiceEntity> invoiceEntities;


}