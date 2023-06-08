package pl.mareczek100.api.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(of = "pesel")
public class MechanicDTO {

    String name;
    String surname;
    String pesel;


}
