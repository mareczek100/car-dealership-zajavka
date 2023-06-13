package pl.mareczek100.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mareczek100.api.dto.AddressDTO;
import pl.mareczek100.api.dto.CustomerDTO;
import pl.mareczek100.api.dto.dtomapper.CustomerDtoMapper;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.service.CustomerService;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

private final CustomerService customerService;
private final CustomerDtoMapper customerDtoMapper;

    @GetMapping
    public String homePage() {
        return "customer";
    }

    @GetMapping("/create_customer")
    public String createCustomer() {
        return "create_customer";
    }

    @PostMapping("/create_customer/add")
    public String addNewCustomer(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("street") String street,
            @RequestParam("buildingFlatNumber") String buildingFlatNumber
    ) {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name(name)
                .surname(surname)
                .phone(phone)
                .email(email)
                .address(
                        AddressDTO.builder()
                                .country(country)
                                .city(city)
                                .postalCode(postalCode)
                                .street(street)
                                .buildingFlatNumber(buildingFlatNumber)
                                .build()
                )
                .build();

        Customer customer = customerDtoMapper.mapFromDTO(customerDTO);
        customerService.insertCustomer(customer);

        return "customer";
    }


}