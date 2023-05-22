package pl.mareczek100.service.dao;

import pl.mareczek100.domain.Mechanic;

import java.util.List;
import java.util.Optional;


public interface MechanicRepository {


    void mechanicInit(Mechanic mechanic);

    Optional<Mechanic> findMechanic(String pesel);

    List<Mechanic> findAllMechanics();
}
