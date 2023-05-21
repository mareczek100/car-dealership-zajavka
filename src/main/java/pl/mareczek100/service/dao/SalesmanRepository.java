package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.SalesmanEntity;

import java.util.List;
import java.util.Optional;


public interface SalesmanRepository {

    void salesmanInit(SalesmanEntity salesmanEntity);

    Optional<SalesmanEntity> findSalesman(String pesel);

    List<SalesmanEntity> findAllSalesman();
}