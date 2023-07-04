package pl.mareczek100.api.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mareczek100.api.dto.AddressDTO;
import pl.mareczek100.api.dto.CustomerDTO;
import pl.mareczek100.api.dto.dtomapper.CustomerDtoMapper;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerRestController {

private final CustomerService customerService;
private final CustomerDtoMapper customerDtoMapper;

    @PostMapping("/create_customer/add")
    public CustomerDTO addNewCustomer(
            @Valid @RequestBody CustomerDTO customerDTO
    ) {
        CustomerDTO customerToSaveDTO = CustomerDTO.builder()
                .name(customerDTO.name())
                .surname(customerDTO.surname())
                .phone(customerDTO.phone())
                .email(customerDTO.email())
                .address(
                        AddressDTO.builder()
                                .country(customerDTO.address().country())
                                .city(customerDTO.address().city())
                                .postalCode(customerDTO.address().postalCode())
                                .street(customerDTO.address().street())
                                .buildingFlatNumber(customerDTO.address().buildingFlatNumber())
                                .build()
                )
                .build();

        Customer customer = customerDtoMapper.mapFromDTO(customerToSaveDTO);
        Customer insertedCustomer = customerService.insertCustomer(customer);

        return customerDtoMapper.mapToDTO(insertedCustomer);
    }


}