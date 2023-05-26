package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.CarServiceHandlingEntity;


public interface CarServiceHandlingJpaRepository extends JpaRepository<CarServiceHandlingEntity, Integer> {


}
