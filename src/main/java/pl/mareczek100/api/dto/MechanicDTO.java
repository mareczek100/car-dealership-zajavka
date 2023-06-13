package pl.mareczek100.api.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Builder
public record MechanicDTO (String name,
                           String surname,
                           String pesel){

}
