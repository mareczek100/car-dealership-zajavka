package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.CarServicePartsEntity;


public interface CarServicePartsRepository extends JpaRepository<Object, Integer> {

    void carServicePartsInit(CarServicePartsEntity carServicePartsEntity);

}
