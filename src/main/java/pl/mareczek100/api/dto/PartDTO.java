package pl.mareczek100.api.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;

@Builder

public record PartDTO  (String serialNumber,
                        String description,
                        BigDecimal price){




}
