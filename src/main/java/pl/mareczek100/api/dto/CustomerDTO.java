package pl.mareczek100.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CustomerDTO (
        String name,
        String surname,
        @Size(min = 7, max = 15)
        @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
        String phone,
        @Email
        String email,
        AddressDTO address){

}
