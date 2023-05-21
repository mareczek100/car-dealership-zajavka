package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.CarServiceHandlingEntity;


public interface CarServiceHandlingRepository extends JpaRepository<Object, Integer> {


    void carServiceHandlingInit(CarServiceHandlingEntity carServiceHandlingEntity);
}
