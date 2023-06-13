package pl.mareczek100.api.dto;

import lombok.Builder;

@Builder
public record MechanicDTO (String name,
                           String surname,
                           String pesel){

}
