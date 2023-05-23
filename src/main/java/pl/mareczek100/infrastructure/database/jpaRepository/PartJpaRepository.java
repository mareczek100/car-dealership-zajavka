package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.PartEntity;

import java.util.Optional;

public interface PartJpaRepository extends JpaRepository<PartEntity, Integer> {
    Optional<PartEntity> findPartBySerialNumber(String serialNumber);

}
