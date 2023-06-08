package pl.mareczek100.api.dto;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class CarServicePartsDTO {

    Short quantity;
    CarServiceRequestDTO carServiceRequest;
    PartDTO part;
}
