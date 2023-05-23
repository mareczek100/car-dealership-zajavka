package pl.mareczek100.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(of = "carServiceHandlingId")
public class CarServiceHandling {

     Integer carServiceHandlingId;
     Short hours;
     String comment;
     CarServiceRequest carServiceRequest;
     Mechanic mechanic;
     Service service;

}
