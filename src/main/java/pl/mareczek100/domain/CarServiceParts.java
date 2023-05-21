package pl.mareczek100.domain;

import lombok.*;
import pl.mareczek100.domain.inputTrafficData.CarServiceRequestInputData;

@With
@Value
@Builder
@EqualsAndHashCode(of = "carServicePartsId")
@ToString(exclude = "part")
public class CarServiceParts {

    Integer carServicePartsId;
    Short quantity;
    CarServiceRequestInputData carServiceRequest;
    Part part;
}
