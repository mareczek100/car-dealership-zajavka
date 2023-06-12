package pl.mareczek100.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

import java.time.OffsetDateTime;
import java.util.List;

@Controller
@RequestMapping("/handling")
@RequiredArgsConstructor
public class ServiceHandlingController {



    @GetMapping
    public String homePage() {

        return "service_handling";
    }


    @PostMapping("/process")
    public String createServiceRequestByVin(

    ) {

        return "service_handling";
    }


}