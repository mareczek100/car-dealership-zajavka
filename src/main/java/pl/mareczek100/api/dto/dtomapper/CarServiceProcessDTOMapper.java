package pl.mareczek100.api.dto.dtomapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mareczek100.api.dto.*;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.CarServiceRequest;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class CarServiceProcessDTOMapper {


    private final CustomerDtoMapper customerDtoMapper;
    private final MechanicDtoMapper mechanicDtoMapper;
    private final ServiceDtoMapper serviceDtoMapper;
    private final CarDtoMapper carDtoMapper;
    private final PartDtoMapper partDtoMapper;

    public CarServiceProcessDTO mapToDTO(CarServiceRequest serviceRequest) {
        return CarServiceProcessDTO.builder()
                .carServiceRequestNumber(serviceRequest.getCarServiceRequestNumber())
                .receivedDateTime(serviceRequest.getReceivedDateTime())
                .completedDateTime(serviceRequest.getCompletedDateTime())
                .customerComment(serviceRequest.getComment())
                .customer(customerDtoMapper.mapToDTO(serviceRequest.getCustomer()))
                .carToService(carDtoMapper.mapToCarServiceDTO(serviceRequest.getCarToService()))
                .workHours(getHandlingHours(serviceRequest.getCarServiceHandling()))
                .mechanicComment(getMechanicComments(serviceRequest.getCarServiceHandling()))
                .mechanic(getMechanics(serviceRequest.getCarServiceHandling()))
                .service(getService(serviceRequest.getCarServiceHandling()))
                .partQuantity(getPartQuantity(serviceRequest.getCarServicePart()))
                .parts(getParts(serviceRequest.getCarServicePart()))
                .build();
    }

    private List<PartDTO> getParts(Set<CarServiceParts> carServicePart) {
        return carServicePart.stream()
                .map(CarServiceParts::getPart)
                .map(partDtoMapper::mapToDTO)
                .toList();
    }

    private List<Short> getPartQuantity(Set<CarServiceParts> carServicePart) {
        return carServicePart.stream()
                .map(CarServiceParts::getQuantity)
                .toList();
    }

    private List<ServiceDTO> getService(Set<CarServiceHandling> carServiceHandling) {
        return carServiceHandling.stream()
                .map(CarServiceHandling::getService)
                .map(serviceDtoMapper::mapToDTO)
                .toList();
    }

    private List<MechanicDTO> getMechanics(Set<CarServiceHandling> carServiceHandling) {
        return carServiceHandling.stream()
                .map(CarServiceHandling::getMechanic)
                .map(mechanicDtoMapper::mapToDTO)
                .toList();
    }

    private List<String> getMechanicComments(Set<CarServiceHandling> carServiceHandling) {
        return carServiceHandling.stream()
                .map(CarServiceHandling::getComment)
                .toList();
    }

    private Short getHandlingHours(Set<CarServiceHandling> carServiceHandling) {
        return carServiceHandling.stream()
                .map(CarServiceHandling::getHours)
                .reduce((a, b) -> (short) (a + b))
                .orElse((short) 0);
    }

}
