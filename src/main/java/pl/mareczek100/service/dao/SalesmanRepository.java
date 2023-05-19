package pl.mareczek100.service.dao;

import pl.mareczek100.infrastructure.database.entity.Salesman;

import java.util.List;
import java.util.Optional;


public interface SalesmanRepository {

    void salesmanInit(Salesman salesman);

    Optional<Salesman> findSalesman(String pesel);

    List<Salesman> findAllSalesman();
}