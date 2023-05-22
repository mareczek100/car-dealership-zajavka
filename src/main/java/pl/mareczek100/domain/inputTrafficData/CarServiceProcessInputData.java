package pl.mareczek100.domain.inputTrafficData;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class CarServiceProcessInputData {
    String mechanicPesel;
    String carVin;
    String partSerialNumber;
    Short partQuantity;
    String serviceCode;
    Short serviceHandlingHours;
    String mechanicComment;
    Boolean finished;

}
