package pl.mareczek100.domain.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.SalesmanEntity;
import pl.mareczek100.service.dao.SalesmanRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class SalesmanRepositoryImpl implements SalesmanRepository {


    @Override
    public void salesmanInit(SalesmanEntity salesmanEntity) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.persist(salesmanEntity);
            transaction.commit();
        }
    }

    @Override
    public Optional<SalesmanEntity> findSalesman(String pesel) {
        Optional<SalesmanEntity> salesman;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            salesman = session.createQuery
                            ("FROM Salesman sal WHERE sal.pesel = :pesel", SalesmanEntity.class)
                    .setParameter("pesel", pesel)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return salesman;
    }

    @Override
    public List<SalesmanEntity> findAllSalesman() {
        List<SalesmanEntity> salesmanEntityList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            salesmanEntityList = session.createQuery("FROM Salesman", SalesmanEntity.class).getResultList();
            transaction.commit();
        }
        return salesmanEntityList;
    }
}