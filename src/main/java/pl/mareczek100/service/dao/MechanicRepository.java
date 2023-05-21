package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.MechanicEntity;

import java.util.List;
import java.util.Optional;


public interface MechanicRepository {


    void mechanicInit(MechanicEntity mechanicEntity);

    Optional<MechanicEntity> findMechanic(String pesel);

    List<MechanicEntity> findAllMechanics();
}
