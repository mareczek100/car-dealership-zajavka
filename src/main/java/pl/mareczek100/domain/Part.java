package pl.mareczek100.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "serialNumber")
@ToString(exclude = "carServiceParts")
public class Part {

    Integer partId;
    String serialNumber;
    String description;
    BigDecimal price;
    Set<CarServiceParts> carServiceParts;

}
