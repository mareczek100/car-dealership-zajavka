package pl.mareczek100.api.dto;

import lombok.Builder;
import lombok.Value;

@Builder
public record AddressDTO(String country,
                         String city,
                         String postalCode,
                         String street,
                         String buildingFlatNumber) {
}
