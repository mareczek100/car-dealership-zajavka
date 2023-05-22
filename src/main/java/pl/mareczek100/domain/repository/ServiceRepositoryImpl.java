package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.Service;
import pl.mareczek100.infrastructure.database.jpaRepository.SalesmanJpaRepository;
import pl.mareczek100.infrastructure.database.jpaRepository.ServiceJpaRepository;
import pl.mareczek100.service.dao.ServiceRepository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class ServiceRepositoryImpl implements ServiceRepository {

    private final ServiceJpaRepository serviceJpaRepository;
    private final ServiceEntityMapper serviceEntityMapper;
    @Override
    public List<Service> findAllServices() {
        return serviceJpaRepository.findAll().stream()
                .map(serviceEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<Service> findService(String serviceCode) {
        return serviceJpaRepository.findByServiceCode(serviceCode)
                .map(serviceEntityMapper::mapFromEntity);
    }

}
