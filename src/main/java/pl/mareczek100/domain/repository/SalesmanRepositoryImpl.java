package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.Salesman;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entityMapper.SalesmanEntityMapper;
import pl.mareczek100.infrastructure.database.jpaRepository.SalesmanJpaRepository;
import pl.mareczek100.service.dao.SalesmanRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class SalesmanRepositoryImpl implements SalesmanRepository {

private final SalesmanJpaRepository salesmanJpaRepository;
private final SalesmanEntityMapper salesmanEntityMapper;

    @Override
    public Optional<Salesman> findSalesman(String pesel) {
        return salesmanJpaRepository.findByPesel(pesel)
                .map(salesmanEntityMapper::mapFromEntity);
    }

    @Override
    public List<Salesman> findAllSalesman() {
        return salesmanJpaRepository.findAll().stream()
                .map(salesmanEntityMapper::mapFromEntity)
                .toList();
    }
}