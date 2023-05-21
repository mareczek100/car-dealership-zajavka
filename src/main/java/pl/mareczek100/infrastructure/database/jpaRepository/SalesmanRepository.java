package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.SalesmanEntity;

import java.util.List;
import java.util.Optional;


public interface SalesmanRepository extends JpaRepository<Object, Integer> {

    void salesmanInit(SalesmanEntity salesmanEntity);

    Optional<SalesmanEntity> findSalesman(String pesel);

    List<SalesmanEntity> findAllSalesman();
}