package pl.mareczek100.api.dto;


import lombok.Builder;

@Builder
public record CustomerDTO (String name, String surname, String phone, String email, AddressDTO address){

}
