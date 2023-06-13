package pl.mareczek100.api.dto;

import lombok.Builder;

@Builder
public record CarServiceDTO
        (String brand,
         String model,
         Short year) {}