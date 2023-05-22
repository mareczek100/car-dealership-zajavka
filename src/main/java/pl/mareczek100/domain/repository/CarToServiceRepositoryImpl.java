package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarHistory;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.domain.CarToService;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.CarToServiceEntity;
import pl.mareczek100.infrastructure.database.jpaRepository.CarToServiceJpaRepository;
import pl.mareczek100.service.dao.CarToServiceRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class CarToServiceRepositoryImpl implements CarToServiceRepository {

    private final CarToServiceJpaRepository carToServiceJpaRepository;
    private final CarToServiceMapper carToServiceMapper;

    @Override
    public CarToService carToServiceInsert(CarToService carToService) {
        CarToServiceEntity carToServiceEntity = carToServiceMapper.mapToEntity(carToService);
        CarToServiceEntity carToServiceEntitySaved = carToServiceJpaRepository.saveAndFlush(carToServiceEntity);
        return carToServiceMapper.mapFromEntity(carToServiceEntitySaved);
    }

    @Override
    public Optional<CarToService> findCarToService(String vin) {
        return carToServiceJpaRepository.findByVin(vin)
                .map(carToServiceMapper::mapFromEntity);
    }

    @Override
    public List<CarToService> findAllCarsToService() {
        return carToServiceJpaRepository.findAll().stream()
                .map(carToServiceMapper::mapFromEntity)
                .toList();
    }

    @Override
    public CarHistory findCarHistoryByVin(String vin) {
        CarToServiceEntity carToServiceHistory = carToServiceJpaRepository.findCarHistoryByVin(vin);
        return carToServiceMapper.mapFromEntity(carToServiceHistory);
    }

    private CarHistory.ServiceRequest mapServiceRequest(CarServiceRequest serviceRequest) {
        return CarHistory.ServiceRequest.builder()
                .serviceRequestNumber(serviceRequest.getCarServiceRequestNumber())
                .receivedDateTime(serviceRequest.getReceivedDateTime())
                .completedDateTime(serviceRequest.getCompletedDateTime())
                .customerComment(serviceRequest.getCustomerComment())
                .services(mapServices(serviceRequest.getCarServiceHandlingEntities().stream().map(CarServiceHandling::getService).toList()))
                .parts(mapParts(serviceRequest.getCarServicePartEntities().stream().map(CarServiceParts::getPart).toList()))
                .build();
    }

    private List<CarHistory.Service> mapServices(List<Service> entities) {
        return entities.stream()
                .map(service -> CarHistory.Service.builder()
                        .serviceCode(service.getServiceCode())
                        .description(service.getDescription())
                        .price(service.getPrice())
                        .build())
                .toList();
    }

    private List<CarHistory.Part> mapParts(List<Part> entities) {
        return entities.stream()
                .map(part -> CarHistory.Part.builder()
                        .serialNumber(part.getSerialNumber())
                        .description(part.getDescription())
                        .price(part.getPrice())
                        .build())
                .toList();
    }

}