package pl.mareczek100.api.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mareczek100.api.dto.AddressDTO;
import pl.mareczek100.api.dto.CarDTO;
import pl.mareczek100.api.dto.CarServiceProcessDTO;
import pl.mareczek100.api.dto.CustomerDTO;
import pl.mareczek100.api.dto.dtomapper.CarDtoMapper;
import pl.mareczek100.api.dto.dtomapper.CarServiceProcessDTOMapper;
import pl.mareczek100.api.dto.dtomapper.CustomerDtoMapper;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.domain.CarToService;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.service.CarServiceRequestService;

import java.util.List;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class CarServiceRestController {

    private final CarServiceRequestService carServiceRequestService;
    private final CarServiceProcessDTOMapper carServiceProcessDTOMapper;
    private final CustomerDtoMapper customerDtoMapper;
    private final CarDtoMapper carDtoMapper;


    @PostMapping("/request")
    public String createServiceRequestByVin(
            @RequestParam("vin") String vin,
            @RequestParam("comment") String comment
    ) {
        CarServiceRequest requestInner = carServiceRequestService.createCarServiceRequestInner(vin, comment);
        carServiceRequestService.insertCarServiceRequest(requestInner);

        String serviceConfirm = "Your car [%s] is in our mechanics hands right now!%n".formatted(vin);
        String progress = "Your can check progress in Progress portal!";


        return serviceConfirm.concat(progress);
    }

    @PostMapping("/service_request_new")
    public String createServiceRequestByNewUser(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @Valid @RequestParam("phone") String phone,
            @Valid @RequestParam("email") String email,
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

        CarDTO carDTO = CarDTO.builder()
                .vin(vin)
                .brand(brand)
                .model(model)
                .year(year)
                .color(color)
                .build();

        CarToService carToService = carDtoMapper.mapFromDTO(carDTO);

        CarServiceRequest requestOuter = carServiceRequestService.createCarServiceRequestOuter(customer, carToService, comment);
        carServiceRequestService.insertCarServiceRequest(requestOuter);

        String serviceConfirm = "Your car [%s] is in our mechanics hands right now!%n".formatted(vin);
        String progress = "Your can check progress in Progress portal!";


        return serviceConfirm.concat(progress);
    }


    @PostMapping("/progress")
    public List<CarServiceProcessDTO> checkServiceProgress(
            @RequestParam("vin") String vin
    ) {
        List<CarServiceRequest> serviceRequests = carServiceRequestService.findCarServiceRequests(vin);

        return serviceRequests.stream()
                .map(carServiceProcessDTOMapper::mapToDTO)
                .toList();
    }

}