package pl.mareczek100.infrastructure.database.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.CarToSellTempStorage;
import pl.mareczek100.service.dao.CarToSellTempStorageRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class CarToSellTempStorageRepositoryImpl implements CarToSellTempStorageRepository {
    @Override
    public void carToSellTempStorageInit(CarToSellTempStorage carToSellTempStorage) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.merge(carToSellTempStorage);
            transaction.commit();
        }
    }

    @Override
    public Optional<CarToSellTempStorage> findCarToSellTempStorage(String vin) {
        Optional<CarToSellTempStorage> carToSellTempStorage;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carToSellTempStorage = session.createQuery
                            ("FROM CarToSellTempStorage car WHERE car.vin = :vin", CarToSellTempStorage.class)
                    .setParameter("vin", vin)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return carToSellTempStorage;
    }

    @Override
    public List<CarToSellTempStorage> findAllCarsToSellTempStorage() {
        List<CarToSellTempStorage> carToSellTempStorage;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carToSellTempStorage = session.createQuery("FROM CarToSellTempStorage",
                    CarToSellTempStorage.class).getResultList();
            transaction.commit();
        }
        return carToSellTempStorage;
    }

    @Override
    public void deleteCarToSellTempStorageByCarVin(String vin) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            String delete = "DELETE FROM CarToSellTempStorage car WHERE car.vin = :vin";
            session.createMutationQuery(delete)
                    .setParameter("vin", vin)
                    .executeUpdate();
            transaction.commit();
        }
    }
    @Override
    public void deleteCarToSellTempStorage(CarToSellTempStorage carToSellTempStorage) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.remove(carToSellTempStorage);
            transaction.commit();
        }
    }

}