package pl.mareczek100.api.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/api/service")
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
            @Valid @RequestBody CustomerDTO customerDTO,
            @RequestBody CarDTO carDTO,
            @RequestParam("comment") String comment
    ) {
        CustomerDTO customerToRequestDTO = CustomerDTO.builder()
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

        Customer customer = customerDtoMapper.mapFromDTO(customerToRequestDTO);

        CarDTO carToRequestDTO = CarDTO.builder()
                .vin(carDTO.vin())
                .brand(carDTO.brand())
                .model(carDTO.model())
                .year(carDTO.year())
                .color(carDTO.color())
                .build();

        CarToService carToService = carDtoMapper.mapFromDTO(carToRequestDTO);

        CarServiceRequest requestOuter = carServiceRequestService.createCarServiceRequestOuter(customer, carToService, comment);
        carServiceRequestService.insertCarServiceRequest(requestOuter);

        String serviceConfirm = "Your car [%s] is in our mechanics hands right now!%n".formatted(carToService.getVin());
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