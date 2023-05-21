package pl.mareczek100.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;
import pl.mareczek100.domain.inputTrafficData.CarServiceRequestInputData;

@With
@Value
@Builder
@EqualsAndHashCode(of = "carServiceHandlingId")
public class CarServiceHandling {

     Integer carServiceHandlingId;
     Short hours;
     String comment;
     CarServiceRequestInputData carServiceRequest;
     Mechanic mechanic;
     Service service;

}
