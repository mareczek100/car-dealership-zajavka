package pl.mareczek100.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarServiceHandlingDTO {

     Short hours;
     String comment;
     CarServiceRequestDTO carServiceRequest;
     MechanicDTO mechanic;
     ServiceDTO service;

}
