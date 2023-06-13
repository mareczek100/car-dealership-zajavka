package pl.mareczek100.api.dto;

import lombok.Builder;

@Builder
public record AddressDTO(String country,
                         String city,
                         String postalCode,
                         String street,
                         String buildingFlatNumber) {
}
