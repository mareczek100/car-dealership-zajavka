package pl.mareczek100.infrastructure.database.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.Salesman;
import pl.mareczek100.service.dao.SalesmanRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class SalesmanRepositoryImpl implements SalesmanRepository {


    @Override
    public void salesmanInit(Salesman salesman) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.persist(salesman);
            transaction.commit();
        }
    }

    @Override
    public Optional<Salesman> findSalesman(String pesel) {
        Optional<Salesman> salesman;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            salesman = session.createQuery
                            ("FROM Salesman sal WHERE sal.pesel = :pesel", Salesman.class)
                    .setParameter("pesel", pesel)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return salesman;
    }

    @Override
    public List<Salesman> findAllSalesman() {
        List<Salesman> salesmanList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            salesmanList = session.createQuery("FROM Salesman", Salesman.class).getResultList();
            transaction.commit();
        }
        return salesmanList;
    }
}