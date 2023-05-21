package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.MechanicEntity;

import java.util.List;
import java.util.Optional;


public interface MechanicRepository extends JpaRepository<Object, Integer> {


    void mechanicInit(MechanicEntity mechanicEntity);

    Optional<MechanicEntity> findMechanic(String pesel);

    List<MechanicEntity> findAllMechanics();
}
