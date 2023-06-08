package pl.mareczek100.api.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "serviceCode")
@ToString(exclude = "carServiceHandlings")
public class ServiceDTO {

    String serviceCode;
    String description;
    BigDecimal price;
    Set<CarServiceHandlingDTO> carServiceHandlings;

}
