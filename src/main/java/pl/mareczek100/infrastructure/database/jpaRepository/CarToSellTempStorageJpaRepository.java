package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.CarToSellTempStorageEntity;

import java.util.Optional;


public interface CarToSellTempStorageJpaRepository extends JpaRepository<CarToSellTempStorageEntity, Integer> {
    Optional<CarToSellTempStorageEntity> findByVin(String vin);
    void deleteCarToSellTempStorageByVin(String vin);

}