package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.PartEntity;

import java.util.List;
import java.util.Optional;


public interface PartRepository extends JpaRepository<Object, Integer> {


    void partInit(PartEntity partEntity);

    Optional<PartEntity> findPart(String serialNumber);

    List<PartEntity> findAllParts();

}
