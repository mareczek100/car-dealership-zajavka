package pl.mareczek100.service;

import lombok.Value;
import pl.mareczek100.infrastructure.data_storage.ServiceDataStorage;
import pl.mareczek100.infrastructure.database.entity.ServiceEntity;
import pl.mareczek100.service.dao.ServiceRepository;

import java.util.List;

@Value
@org.springframework.stereotype.Service
public class ServiceService {
    ServiceRepository serviceRepository;
    ServiceDataStorage serviceDataStorage;


    public void serviceInit() {
        serviceDataStorage.createService()
                .forEach(serviceRepository::serviceInit);
    }

    public List<ServiceEntity> findAllServices() {
        List<ServiceEntity> allServiceEntity = serviceRepository.findAllServices();
        if (allServiceEntity.isEmpty()){
            throw  new RuntimeException("No service's at all!");
        }
        return allServiceEntity;
    }

    public ServiceEntity findService(String serviceCode){
        return serviceRepository.findService(serviceCode)
                .orElseThrow(() -> new RuntimeException("Sorry, Service [%s] didn't exist!".formatted(serviceCode)));
    }

}
