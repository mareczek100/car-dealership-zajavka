package pl.mareczek100.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class CarPurchaseInputData {

    String customerName;
    String customerSurname;
    String customerPhone;
    String customerEmail;
    String customerAddressCountry;
    String customerAddressCity;
    String customerAddressPostalCode;
    String customerAddressStreet;
    String customerAddressBuildingFlatNumber;
    String carVin;
    String salesmanPesel;

}
