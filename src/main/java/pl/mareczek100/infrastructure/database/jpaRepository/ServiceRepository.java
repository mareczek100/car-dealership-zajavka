package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.ServiceEntity;

import java.util.List;
import java.util.Optional;


public interface ServiceRepository extends JpaRepository<Object, Integer> {


    void serviceInit(ServiceEntity serviceEntity);

    List<ServiceEntity> findAllServices();

    Optional<ServiceEntity> findService(String serviceCode);
}
