package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarServiceRequest;
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
    public Optional<CarServiceRequest> findCarServiceRequestsByCarVin(String vin) {
     return carServiceRequestJpaRepository.findCarServiceRequestsByCarVin(vin).stream()
             .map(entity -> carServiceRequestEntityMapper.mapFromEntity(entity))
             .toList();
    }
    @Override
    public List<CarServiceRequest> findAllCarServiceRequest() {
        return carServiceRequestJpaRepository.findAll().stream()
                .map(carServiceRequestEntityMapper::mapFromEntity)
                .toList();
    }
}
