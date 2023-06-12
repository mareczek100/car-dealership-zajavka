package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequestEntity;
import pl.mareczek100.infrastructure.database.entityMapper.CarServiceRequestEntityMapper;
import pl.mareczek100.infrastructure.database.jpaRepository.CarServiceRequestJpaRepository;
import pl.mareczek100.service.dao.CarServiceRequestRepository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class CarServiceRequestRepositoryImpl implements CarServiceRequestRepository {
    private final CarServiceRequestJpaRepository carServiceRequestJpaRepository;
    private final CarServiceRequestEntityMapper carServiceRequestEntityMapper;

    @Override
    public CarServiceRequest insertCarServiceRequest(CarServiceRequest carServiceRequest) {
        CarServiceRequestEntity carServiceRequestEntity = carServiceRequestEntityMapper.mapToEntity(carServiceRequest);
        CarServiceRequestEntity savedServiceRequestEntity = carServiceRequestJpaRepository.saveAndFlush(carServiceRequestEntity);
        return carServiceRequestEntityMapper.mapFromEntity(savedServiceRequestEntity);
    }

    @Override
    public List<CarServiceRequest> findCarServiceRequestsByCarVin(String vin) {
        return carServiceRequestJpaRepository.findCarServiceRequestsByCarVin(vin).stream()
                .map(carServiceRequestEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<CarServiceRequest> findAllCarServiceRequest() {
        return carServiceRequestJpaRepository.findAll().stream()
                .map(carServiceRequestEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<CarServiceRequest> findCarServiceRequestByCarServiceRequestNumber(String requestNumber) {
        return carServiceRequestJpaRepository.findCarServiceRequestByCarServiceRequestNumber(requestNumber)
                .map(carServiceRequestEntityMapper::mapFromEntity);
    }
}
