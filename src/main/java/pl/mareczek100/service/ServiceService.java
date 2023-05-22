package pl.mareczek100.service;

import lombok.Value;
import pl.mareczek100.domain.Service;
import pl.mareczek100.service.dao.ServiceRepository;

import java.util.List;

@Value
@org.springframework.stereotype.Service
public class ServiceService {
    ServiceRepository serviceRepository;

    public List<Service> findAllServices() {
        List<Service> allService = serviceRepository.findAllServices();
        if (allService.isEmpty()){
            throw  new RuntimeException("No service's at all!");
        }
        return allService;
    }

    public Service findService(String serviceCode){
        return serviceRepository.findService(serviceCode)
                .orElseThrow(() -> new RuntimeException("Sorry, Service [%s] didn't exist!".formatted(serviceCode)));
    }

}
