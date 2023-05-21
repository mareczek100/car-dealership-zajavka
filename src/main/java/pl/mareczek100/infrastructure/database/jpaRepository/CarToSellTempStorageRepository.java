package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.CarToSellTempStorageEntity;

import java.util.List;
import java.util.Optional;


public interface CarToSellTempStorageRepository extends JpaRepository<Object, Integer> {

    void carToSellTempStorageInit(CarToSellTempStorageEntity carToSellTempStorageEntity);
    Optional<CarToSellTempStorageEntity> findCarToSellTempStorage(String vin);
    List<CarToSellTempStorageEntity> findAllCarsToSellTempStorage();
    void deleteCarToSellTempStorageByCarVin(String vin);
    void deleteCarToSellTempStorage(CarToSellTempStorageEntity carToSellTempStorageEntity);
}