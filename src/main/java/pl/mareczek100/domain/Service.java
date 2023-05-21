package pl.mareczek100.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "serviceCode")
@ToString(exclude = "carServiceHandlings")
public class Service {
    Integer serviceId;
    String serviceCode;
    String description;
    BigDecimal price;
    Set<CarServiceHandling> carServiceHandlings;

}
