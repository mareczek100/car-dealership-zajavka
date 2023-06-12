package pl.mareczek100.api.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CarServiceDTO
        (String brand,
         String model,
         Short year) {}