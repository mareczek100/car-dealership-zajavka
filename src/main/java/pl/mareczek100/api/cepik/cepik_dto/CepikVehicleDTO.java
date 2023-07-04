package pl.mareczek100.api.cepik.cepik_dto;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record CepikVehicleDTO (
        String brand,
        String model,
        String type,
        BigDecimal engineCapacity,
        Integer weight,
        String fuel
){}