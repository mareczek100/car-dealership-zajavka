package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequestEntity;

import java.util.List;
import java.util.Optional;


public interface CarServiceRequestRepository extends JpaRepository<Object, Integer> {


    void insertCarServiceRequest(CarServiceRequestEntity carServiceRequestEntity);

    Optional<CarServiceRequestEntity> findCarServiceRequestsByCarVin(String vin);

    List<CarServiceRequestEntity> findAllCarServiceRequest();
}
