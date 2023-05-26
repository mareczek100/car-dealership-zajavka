package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.infrastructure.database.entity.CarServiceHandlingEntity;
import pl.mareczek100.infrastructure.database.entity.CarServicePartsEntity;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequestEntity;
import pl.mareczek100.infrastructure.database.entityMapper.CarServiceHandlingEntityMapper;
import pl.mareczek100.infrastructure.database.entityMapper.CarServicePartsEntityMapper;
import pl.mareczek100.infrastructure.database.entityMapper.CarServiceRequestEntityMapper;
import pl.mareczek100.infrastructure.database.jpaRepository.CarServiceHandlingJpaRepository;
import pl.mareczek100.infrastructure.database.jpaRepository.CarServicePartsJpaRepository;
import pl.mareczek100.infrastructure.database.jpaRepository.CarServiceRequestJpaRepository;
import pl.mareczek100.service.dao.ServiceRequestProcessingRepository;

@Repository
@AllArgsConstructor
public class ServiceRequestProcessingRepositoryImpl implements ServiceRequestProcessingRepository {

    private final CarServiceHandlingJpaRepository serviceHandlingJpaRepository;
    private final CarServiceRequestJpaRepository carServiceRequestJpaRepository;
    private final CarServicePartsJpaRepository carServicePartsJpaRepository;
    private final CarServiceHandlingEntityMapper serviceHandlingEntityMapper;
    private final CarServicePartsEntityMapper servicePartsEntityMapper;
    private final CarServiceRequestEntityMapper carServiceRequestEntityMapper;

    @Override
    public void serviceRequestProcess(CarServiceHandling carServiceHandling, CarServiceParts carServiceParts) {

        CarServicePartsEntity servicePartsEntity = servicePartsEntityMapper.mapToEntity(carServiceParts);
        carServicePartsJpaRepository.saveAndFlush(servicePartsEntity);

        CarServiceHandlingEntity serviceHandlingEntity = serviceHandlingEntityMapper.mapToEntity(carServiceHandling);
        CarServiceHandlingEntity savedServiceHandlingEntity = serviceHandlingJpaRepository.saveAndFlush(serviceHandlingEntity);

        CarServiceRequestEntity serviceRequestEntity = savedServiceHandlingEntity.getCarServiceRequest();
        carServiceRequestJpaRepository.saveAndFlush(serviceRequestEntity);

    }
}