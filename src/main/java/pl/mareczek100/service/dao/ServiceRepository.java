package pl.mareczek100.service.dao;

import pl.mareczek100.domain.Service;

import java.util.List;
import java.util.Optional;


public interface ServiceRepository {

    List<Service> findAllServices();

    Optional<Service> findService(String serviceCode);
}
