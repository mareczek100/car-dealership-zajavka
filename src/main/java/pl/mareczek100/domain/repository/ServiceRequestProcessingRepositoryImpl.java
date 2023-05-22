package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.CarServiceHandling;
import pl.mareczek100.domain.CarServiceParts;
import pl.mareczek100.domain.CarServiceRequest;
import pl.mareczek100.infrastructure.database.entity.CarServiceHandlingEntity;
import pl.mareczek100.infrastructure.database.entity.CarServicePartsEntity;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequestEntity;
import pl.mareczek100.infrastructure.database.entity.PartEntity;
import pl.mareczek100.infrastructure.database.entityMapper.CarServiceHandlingEntityMapper;
import pl.mareczek100.infrastructure.database.entityMapper.CarServicePartsEntityMapper;
import pl.mareczek100.infrastructure.database.jpaRepository.CarServiceHandlingJpaRepository;
import pl.mareczek100.infrastructure.database.jpaRepository.CarServicePartsJpaRepository;
import pl.mareczek100.infrastructure.database.jpaRepository.CarServiceRequestJpaRepository;
import pl.mareczek100.infrastructure.database.jpaRepository.PartJpaRepository;
import pl.mareczek100.service.dao.ServiceRequestProcessingRepository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ServiceRequestProcessingRepositoryImpl implements ServiceRequestProcessingRepository {

    private final CarServiceHandlingJpaRepository serviceHandlingJpaRepository;
    private final CarServiceRequestJpaRepository carServiceRequestJpaRepository;
    private final PartJpaRepository partJpaRepository;
    private final CarServicePartsJpaRepository carServicePartsJpaRepository;
    private final CarServiceHandlingEntityMapper serviceHandlingEntityMapper;
    private final CarServicePartsEntityMapper servicePartsEntityMapper;

    @Override
    public void serviceRequestProcess
            (CarServiceRequest carServiceRequest, CarServiceHandling carServiceHandling, CarServiceParts carServiceParts) {

        Optional<PartEntity> partEntity = partJpaRepository.findById(carServiceParts.getPart().getPartId());
        CarServicePartsEntity servicePartsEntity = servicePartsEntityMapper.mapToEntity(carServiceParts);
        partEntity.ifPresent(servicePartsEntity::setPartEntity);
        carServicePartsJpaRepository.saveAndFlush(servicePartsEntity);

        CarServiceHandlingEntity serviceHandlingEntity = serviceHandlingEntityMapper.mapToEntity(carServiceHandling);
        serviceHandlingJpaRepository.saveAndFlush(serviceHandlingEntity);

        CarServiceRequestEntity serviceRequestEntity = carServiceRequestJpaRepository.findById(
                carServiceRequest.getCarServiceRequestId()).orElseThrow();
        carServiceRequestJpaRepository.saveAndFlush(serviceRequestEntity);
    }
}