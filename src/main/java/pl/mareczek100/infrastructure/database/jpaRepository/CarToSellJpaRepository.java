package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;

import java.util.Optional;


public interface CarToSellJpaRepository extends JpaRepository<CarToSellEntity, Integer> {
    Optional<CarToSellEntity> findByVin(String vin);
    void deleteByVin(String vin);
}