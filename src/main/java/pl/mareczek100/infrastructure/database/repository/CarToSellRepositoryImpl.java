package pl.mareczek100.infrastructure.database.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.CarToSell;
import pl.mareczek100.service.dao.CarToSellRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class CarToSellRepositoryImpl implements CarToSellRepository {
    @Override
    public void carToSellInit(CarToSell carToSell) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.persist(carToSell);
            transaction.commit();
        }
    }

    @Override
    public Optional<CarToSell> findCarToSell(String vin) {
        Optional<CarToSell> carToSell;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carToSell = session.createQuery
                            ("FROM CarToSell car WHERE car.vin = :vin", CarToSell.class)
                    .setParameter("vin", vin)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return carToSell;
    }

    @Override
    public List<CarToSell> findAllCarsToSell() {
        List<CarToSell> carToSell;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carToSell = session.createQuery("FROM CarToSell", CarToSell.class).getResultList();
            transaction.commit();
        }
        return carToSell;
    }

    @Override
    public void deleteCar(String vin) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            String delete = "DELETE FROM CarToSell car WHERE car.vin = :vin";
            session.createMutationQuery(delete)
                    .setParameter("vin", vin)
                    .executeUpdate();
            transaction.commit();
        }
    }


}