package pl.mareczek100.api.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;


@Builder
public record ServiceDTO (String serviceCode,
                          String description,
                          BigDecimal price){



}
