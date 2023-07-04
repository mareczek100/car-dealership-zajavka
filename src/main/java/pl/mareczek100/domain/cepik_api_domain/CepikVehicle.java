package pl.mareczek100.domain.cepik_api_domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
@Value
@Builder
public class CepikVehicle {

    String id;
    String brand;
    String model;
    String type;
    BigDecimal engineCapacity;
    Integer weight;
    String fuel;
}
