package pl.mareczek100.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Entity
@ToString(exclude = {"carServiceHandlings","carServiceParts"})
@EqualsAndHashCode(of = "carServiceRequestNumber")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car_service_request")
public class CarServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_service_request_id")
    private Integer carServiceRequestId;
    @Column(name = "car_service_request_number", unique = true)
    private String carServiceRequestNumber;
    @Column(name = "received_date_time")
    private OffsetDateTime receivedDateTime;
    @Column(name = "completed_date_time")
    private OffsetDateTime completedDateTime;
    @Column(name = "customer_comment")
    private String customerComment;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_to_service_id")
    private CarToService carToService;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carServiceRequest")
    private Set<CarServiceHandling> carServiceHandlings;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carServiceRequest")
    private Set<CarServiceParts> carServiceParts;


}
