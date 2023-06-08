package pl.mareczek100.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;


public record SalesmanDTO(String name, String surname, String pesel) {

}