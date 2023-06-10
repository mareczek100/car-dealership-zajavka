package pl.mareczek100.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mareczek100.api.dto.AddressDTO;
import pl.mareczek100.api.dto.CarDTO;
import pl.mareczek100.api.dto.CustomerDTO;
import pl.mareczek100.api.dto.dtomapper.CarDtoMapper;
import pl.mareczek100.api.dto.dtomapper.CustomerDtoMapper;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.service.CarToSellService;
import pl.mareczek100.service.CustomerService;
import pl.mareczek100.service.PurchaseCarService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/service")
@RequiredArgsConstructor
public class CarServiceController {

    private final PurchaseCarService purchaseCarService;
    private final CustomerService customerService;
    private final CustomerDtoMapper customerDtoMapper;
    private final CarToSellService carToSellService;
    private final CarDtoMapper carDtoMapper;

    @GetMapping
    public String homePage() {

        return "service";
    }
    @GetMapping("/request")
    public String serviceRequest() {

        return "service_request";
    }
    @GetMapping("/service_request_new")
    public String serviceRequestNewUser() {

        return "service_request_new_user";
    }
    @GetMapping("/progress")
    public String serviceProgress() {

        return "service_progress";
    }

    @PostMapping("/service_request_new/add")
    public String addNewCustomer(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("street") String street,
            @RequestParam("buildingFlatNumber") String buildingFlatNumber,
            @RequestParam("vin") String vin,
            @RequestParam("brand") String brand,
            @RequestParam("model") String model,
            @RequestParam("year") Short year,
            @RequestParam("color") String color,
            @RequestParam("price") BigDecimal price,
            @RequestParam("comment") String comment
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