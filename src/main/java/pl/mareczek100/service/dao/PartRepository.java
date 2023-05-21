package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.PartEntity;

import java.util.List;
import java.util.Optional;


public interface PartRepository {


    void partInit(PartEntity partEntity);

    Optional<PartEntity> findPart(String serialNumber);

    List<PartEntity> findAllParts();

}
