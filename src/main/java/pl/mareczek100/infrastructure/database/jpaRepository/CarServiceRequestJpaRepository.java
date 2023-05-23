package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequestEntity;

import java.util.Optional;


public interface CarServiceRequestJpaRepository extends JpaRepository<CarServiceRequestEntity, Integer> {

    @Query("""
            SELECT csr FROM CarServiceRequestEntity csr
            WHERE csr.carToServiceEntity.vin = :vin
            """)
    Optional<CarServiceRequestEntity> findCarServiceRequestsByCarVin(final @Param("vin") String vin);

}
