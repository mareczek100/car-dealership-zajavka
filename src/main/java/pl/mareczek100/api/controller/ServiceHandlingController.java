package pl.mareczek100.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mareczek100.api.dto.CarServiceProcessDTO;
import pl.mareczek100.api.dto.MechanicDTO;
import pl.mareczek100.api.dto.PartDTO;
import pl.mareczek100.api.dto.ServiceDTO;
import pl.mareczek100.api.dto.dtomapper.CarServiceProcessDTOMapper;
import pl.mareczek100.api.dto.dtomapper.MechanicDtoMapper;
import pl.mareczek100.api.dto.dtomapper.PartDtoMapper;
import pl.mareczek100.api.dto.dtomapper.ServiceDtoMapper;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.domain.Part;
import pl.mareczek100.service.*;

import java.time.OffsetDateTime;
import java.util.List;

@Controller
@RequestMapping("/handling")
@RequiredArgsConstructor
public class ServiceHandlingController {
    private final static String FINISHED = "FINISHED";
    private final static String NONE = "NONE";

    private final CarServiceRequestProcessingService processingService;
    private final CarServiceRequestService carServiceRequestService;
    private final CarServiceProcessDTOMapper serviceProcessDTOMapper;
    private final MechanicService mechanicService;
    private final MechanicDtoMapper mechanicDtoMapper;
    private final ServiceService serviceService;
    private final ServiceDtoMapper serviceDtoMapper;
    private final PartService partService;
    private final PartDtoMapper partDtoMapper;


    @GetMapping
    public String homePage(Model model) {
        List<CarServiceRequest> allCarServiceRequest = carServiceRequestService.findAllCarServiceRequest();
        List<CarServiceProcessDTO> serviceProcessDTOs;
        if (allCarServiceRequest.isEmpty()) {
            return "service_handling";}
        else {
            serviceProcessDTOs = allCarServiceRequest.stream()
                    .map(serviceProcessDTOMapper::mapToDTO)
                    .toList();
        }
        model.addAttribute("serviceProcessDTOs", serviceProcessDTOs);

        return "service_handling";
    }


    @GetMapping("/process")
    public String chooseServiceRequest(
            @RequestParam("requestNumber") String requestNumber,
            Model model
    ) {
        CarServiceRequest carServiceRequest = carServiceRequestService.findCarServiceRequest(requestNumber);
        CarServiceProcessDTO serviceProcessDTO = serviceProcessDTOMapper.mapToDTO(carServiceRequest);
        List<MechanicDTO> mechanicDTOs = mechanicService.findAllMechanics().stream()
                .map(mechanicDtoMapper::mapToDTO)
                .toList();
        List<ServiceDTO> serviceDTOs = serviceService.findAllServices().stream()
                .map(serviceDtoMapper::mapToDTO)
                .toList();
        List<PartDTO> partDTOs = partService.findAllParts().stream()
                .map(partDtoMapper::mapToDTO)
                .toList();

        model.addAttribute("requestNumber", requestNumber);
        model.addAttribute("serviceProcessDTO", serviceProcessDTO);
        model.addAttribute("mechanicDTOs", mechanicDTOs);
        model.addAttribute("serviceDTOs", serviceDTOs);
        model.addAttribute("partDTOs", partDTOs);

        return "service_handling_process";
    }

    @PostMapping("/process/{requestNumber}")
    public String processServiceRequest(
            @PathVariable("requestNumber") String requestNumber,
            @RequestParam("workHours") String workHours,
            @RequestParam("mechanicComment") String mechanicComment,
            @RequestParam("mechanicPesel") String mechanicPesel,
            @RequestParam("serviceCode") String serviceCode,
            @RequestParam("partSerialNumber") String partSerialNumber,
            @RequestParam("partQuantity") String partQuantity,
            @RequestParam("finished") String finished,
            Model model
    ) {

        CarServiceRequest carServiceRequest = carServiceRequestService.findCarServiceRequest(requestNumber);
        CarServiceRequest carServiceRequestProcessed = carServiceRequest.withCompletedDateTime(setCompletedDate(finished));

        CarServiceHandling carServiceHandling = CarServiceHandling.builder()
                .hours(Short.parseShort(workHours))
                .comment(mechanicComment)
                .carServiceRequest(carServiceRequestProcessed)
                .mechanic(mechanicService.findMechanic(mechanicPesel))
                .service(serviceService.findService(serviceCode))
                .build();

        CarServiceParts carServiceParts = CarServiceParts.builder()
                .quantity(Short.parseShort(partQuantity))
                .carServiceRequest(carServiceRequestProcessed)
                .part(findPart(partSerialNumber))
                .build();

        processingService.insertServiceRequestProcess(carServiceHandling, carServiceParts);

        model.addAttribute("dataSaved", "dataSaved");


        return "service_handling_process";
    }

    private Part findPart(String partSerialNumber) {
        if (partSerialNumber.equalsIgnoreCase(NONE)){
            return null;
        }
        return partService.findPart(partSerialNumber);
    }

    private OffsetDateTime setCompletedDate(String finished) {
        if (finished.equalsIgnoreCase(FINISHED)){
            return OffsetDateTime.now();
        }
        return null;
    }


}