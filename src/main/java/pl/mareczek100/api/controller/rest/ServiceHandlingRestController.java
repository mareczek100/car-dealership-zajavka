package pl.mareczek100.api.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mareczek100.api.dto.CarServiceProcessDTO;
import pl.mareczek100.api.dto.MechanicDTO;
import pl.mareczek100.api.dto.PartDTO;
import pl.mareczek100.api.dto.ServiceDTO;
import pl.mareczek100.api.dto.dtomapper.CarServiceProcessDTOMapper;
import pl.mareczek100.api.dto.dtomapper.MechanicDtoMapper;
import pl.mareczek100.api.dto.dtomapper.PartDtoMapper;
import pl.mareczek100.api.dto.dtomapper.ServiceDtoMapper;
import pl.mareczek100.domain.*;
import pl.mareczek100.service.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/handling")
@RequiredArgsConstructor
public class ServiceHandlingRestController {
    private final static String FINISHED = "FINISHED";
    private final static String UNFINISHED = "UNFINISHED";
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
    public ResponseEntity<List<CarServiceProcessDTO>> carServiceProcessHomePage() {
        List<CarServiceRequest> allCarServiceRequest = carServiceRequestService.findAllCarServiceRequest();
        if (allCarServiceRequest.isEmpty()) {
            return ResponseEntity.notFound().build();}

        List<CarServiceProcessDTO> serviceProcessDTOs = allCarServiceRequest.stream()
                    .map(serviceProcessDTOMapper::mapToDTO)
                    .toList();

        return ResponseEntity.accepted().body(serviceProcessDTOs);
    }
    @GetMapping("/parts")
    public ResponseEntity<List<PartDTO>> partsHomePage() {
        List<Part> allParts = partService.findAllParts();
        if (allParts.isEmpty()) {
            return ResponseEntity.notFound().build();}

        List<PartDTO> partDTOs = allParts.stream()
                    .map(partDtoMapper::mapToDTO)
                    .toList();

        return ResponseEntity.accepted().body(partDTOs);
    }
    @GetMapping("/services")
    public ResponseEntity<List<ServiceDTO>> servicesHomePage() {
        List<Service> allServices = serviceService.findAllServices();
        if (allServices.isEmpty()) {
            return ResponseEntity.notFound().build();}

        List<ServiceDTO> serviceDTOs = allServices.stream()
                    .map(serviceDtoMapper::mapToDTO)
                    .toList();

        return ResponseEntity.accepted().body(serviceDTOs);
    }

    @GetMapping("/mechanics")
    public List<MechanicDTO> mechanicsHomePage() {

        return mechanicService.findAllMechanics().stream()
                .map(mechanicDtoMapper::mapToDTO)
                .toList();
    }

    @PostMapping("/process")
    public CarServiceProcessDTO processServiceRequest(
            @RequestParam("requestNumber") String requestNumber,
            @RequestParam("workHours") String workHours,
            @RequestParam("mechanicComment") String mechanicComment,
            @RequestParam("mechanicPesel") String mechanicPesel,
            @RequestParam("serviceCode") String serviceCode,
            @RequestParam("partSerialNumber") String partSerialNumber,
            @RequestParam("partQuantity") String partQuantity,
            @RequestParam("finished") String finished
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

        CarServiceRequest serviceRequest = processingService.insertServiceRequestProcess(carServiceHandling, carServiceParts);

        return serviceProcessDTOMapper.mapToDTO(serviceRequest);
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
        if (finished.equalsIgnoreCase(UNFINISHED)){
            return null;
        }
        throw new DataIntegrityViolationException("Bad type of data. Available types:[\"%s\"] and [\"%s\"]"
                .formatted(FINISHED, UNFINISHED));
    }

}