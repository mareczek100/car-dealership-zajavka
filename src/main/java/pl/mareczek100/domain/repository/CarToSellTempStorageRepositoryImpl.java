package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarToSellTempStorage;
import pl.mareczek100.infrastructure.database.entity.CarToSellTempStorageEntity;
import pl.mareczek100.infrastructure.database.entityMapper.CarToSellTempStorageEntityMapper;
import pl.mareczek100.infrastructure.database.jpaRepository.CarToSellTempStorageJpaRepository;
import pl.mareczek100.service.dao.CarToSellTempStorageRepository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class CarToSellTempStorageRepositoryImpl implements CarToSellTempStorageRepository {

    private final CarToSellTempStorageJpaRepository carToSellTempStorageJpaRepository;
    private final CarToSellTempStorageEntityMapper carToSellTempStorageMapper;

    @Override
    public Optional<CarToSellTempStorage> findCarToSellTempStorage(String vin) {
        return carToSellTempStorageJpaRepository.findByVin(vin)
                .map(carToSellTempStorageMapper::mapFromEntity);
    }

    @Override
    public List<CarToSellTempStorage> findAllCarsToSellTempStorage() {
        return carToSellTempStorageJpaRepository.findAll().stream()
                .map(carToSellTempStorageMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void deleteCarToSellTempStorageByCarVin(String vin) {
        carToSellTempStorageJpaRepository.deleteCarToSellTempStorageByVin(vin);
    }

    @Override
    public void deleteCarToSellTempStorage(CarToSellTempStorage carToSellTempStorage) {
        CarToSellTempStorageEntity carToSellTempStorageEntity = carToSellTempStorageMapper.mapToEntity(carToSellTempStorage);
        carToSellTempStorageJpaRepository.delete(carToSellTempStorageEntity);
    }

}