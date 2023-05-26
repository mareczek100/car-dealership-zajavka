package pl.mareczek100.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "carServicePartsId")
@ToString(exclude = "part")
public class CarServiceParts {

    Integer carServicePartsId;
    Short quantity;
    CarServiceRequest carServiceRequest;
    Part part;
}
