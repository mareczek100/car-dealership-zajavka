package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.OffsetDateTime;

@Data
@Entity
@EqualsAndHashCode(of = "invoiceNumber")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "invoice")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Integer invoiceId;
    @Column(name = "invoice_number", unique = true)
    private String invoiceNumber;
    @Column(name = "date_time")
    private OffsetDateTime dateTime;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_to_sell_id", unique = true)
    @Fetch(FetchMode.JOIN)
    private CarToSellEntity carToSellEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @Fetch(FetchMode.JOIN)
    private CustomerEntity customerEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesman_id")
    @Fetch(FetchMode.JOIN)
    private SalesmanEntity salesmanEntity;

}
