package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarToService;
import pl.mareczek100.infrastructure.database.entity.CarToServiceEntity;
import pl.mareczek100.infrastructure.database.entityMapper.CarToServiceEntityMapper;
import pl.mareczek100.infrastructure.database.jpaRepository.CarToServiceJpaRepository;
import pl.mareczek100.service.dao.CarToServiceRepository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class CarToServiceRepositoryImpl implements CarToServiceRepository {

    private final CarToServiceJpaRepository carToServiceJpaRepository;
    private final CarToServiceEntityMapper carToServiceMapper;

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
    public CarToService findCarHistoryByVin(String vin) {
        CarToServiceEntity carToServiceHistory = carToServiceJpaRepository.findCarHistoryByVin(vin);
        return carToServiceMapper.mapFromEntity(carToServiceHistory);
    }

}