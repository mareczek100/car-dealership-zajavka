package pl.mareczek100.api.dto;

import lombok.Builder;

import java.math.BigDecimal;


@Builder
public record ServiceDTO (String serviceCode,
                          String description,
                          BigDecimal price){
}
