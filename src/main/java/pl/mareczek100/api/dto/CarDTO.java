package pl.mareczek100.api.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CarDTO
        (String vin,
         String brand,
         String model,
         Short year,
         String color,
         BigDecimal price) {}