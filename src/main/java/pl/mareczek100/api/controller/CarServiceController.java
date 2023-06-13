package pl.mareczek100.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import pl.mareczek100.service.CarToSellService;
import pl.mareczek100.service.CustomerService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/service")
@RequiredArgsConstructor
public class CarServiceController {

    private final CarServiceRequestService carServiceRequestService;
    private final CarServiceProcessDTOMapper carServiceProcessDTOMapper;
    private final CustomerDtoMapper customerDtoMapper;
    private final CarDtoMapper carDtoMapper;

    @GetMapping
    public String homePage() {

        return "service";
    }

    @GetMapping("/request")
    public String serviceRequest() {

        return "service_request";
    }

    @PostMapping("/request")
    public String createServiceRequestByVin(
            @RequestParam("vin") String vin,
            @RequestParam("comment") String comment,
            Model model
    ) {
        CarServiceRequest requestInner = carServiceRequestService.createCarServiceRequestInner(vin, comment);
        carServiceRequestService.insertCarServiceRequest(requestInner);

        String serviceConfirm = "Your car [%s] is in our mechanics hands right now!".formatted(vin);
        String progress = "Your can check progress in Progress portal!";

        model.addAttribute("serviceConfirm", serviceConfirm);
        model.addAttribute("progress", progress);
        return "service_request";
    }

    @GetMapping("/service_request_new")
    public String serviceRequestByNewUser() {


        return "service_request_new_user";
    }

    @PostMapping("/service_request_new")
    public String createServiceRequestByNewUser(
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
            @RequestParam("comment") String comment,
            Model viewModel
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

        String serviceConfirm = "Your car [%s] is in our mechanics hands right now!".formatted(vin);
        String progress = "Your can check progress in Progress portal!";

        viewModel.addAttribute("serviceConfirm", serviceConfirm);
        viewModel.addAttribute("progress", progress);

        return "service_request_new_user";
    }

    @GetMapping("/progress")
    public String serviceProgress() {

        return "service_progress";
    }

    @PostMapping("/progress")
    public String checkServiceProgress(
            @RequestParam("vin") String vin,
            Model model
    ) {

        List<CarServiceRequest> serviceRequests = carServiceRequestService.findCarServiceRequests(vin);

        List<CarServiceProcessDTO> serviceProcessDTOs = serviceRequests.stream()
                .map(carServiceProcessDTOMapper::mapToDTO)
                .toList();

        List<OffsetDateTime> completedList = serviceRequests.stream()
                .map(CarServiceRequest::getCompletedDateTime)
                .toList();

        String finished = "You car is finished, take it back!";
        String unfinished = "You car is unfinished, wait a little longer!";

//        model.addAttribute("carVin", vin);
        model.addAttribute("serviceProcessDTOs", serviceProcessDTOs);
        model.addAttribute("unfinished", unfinished);
        model.addAttribute("finished", finished);
        model.addAttribute("completedList", completedList);

        return "service_progress";
    }

}