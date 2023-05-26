package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.CarToServiceEntity;

import java.util.Optional;


public interface CarToServiceJpaRepository extends JpaRepository<CarToServiceEntity, Integer> {
    Optional<CarToServiceEntity> findByVin(String vin);

}