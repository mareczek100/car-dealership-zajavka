package pl.mareczek100.api.dto;

import java.math.BigDecimal;


public record CarDTO
        (String vin,
         String brand,
         String model,
         Short year,
         String color,
         BigDecimal price) {}