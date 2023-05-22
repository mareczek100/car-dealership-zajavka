package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.CarToServiceEntity;

import java.util.Optional;


public interface CarToServiceJpaRepository extends JpaRepository<CarToServiceEntity, Integer> {
    Optional<CarToServiceEntity> findByVin(String vin);
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "carServiceRequestEntities",
                    "carServiceRequestEntities.carServiceHandlingEntities",
                    "carServiceRequestEntities.carServiceHandlingEntities.serviceEntity",
                    "carServiceRequestEntities.carServicePartEntities",
                    "carServiceRequestEntities.carServicePartEntities.partEntity",
            }
    )
    CarToServiceEntity findCarHistoryByVin(String vin);
}