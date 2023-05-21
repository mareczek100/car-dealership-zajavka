package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.ServiceEntity;

import java.util.List;
import java.util.Optional;


public interface ServiceRepository {


    void serviceInit(ServiceEntity serviceEntity);

    List<ServiceEntity> findAllServices();

    Optional<ServiceEntity> findService(String serviceCode);
}
