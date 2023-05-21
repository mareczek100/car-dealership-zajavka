package pl.mareczek100.domain.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.CarToSellTempStorageEntity;
import pl.mareczek100.service.dao.CarToSellTempStorageRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class CarToSellTempStorageRepositoryImpl implements CarToSellTempStorageRepository {
    @Override
    public void carToSellTempStorageInit(CarToSellTempStorageEntity carToSellTempStorageEntity) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.merge(carToSellTempStorageEntity);
            transaction.commit();
        }
    }

    @Override
    public Optional<CarToSellTempStorageEntity> findCarToSellTempStorage(String vin) {
        Optional<CarToSellTempStorageEntity> carToSellTempStorage;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carToSellTempStorage = session.createQuery
                            ("FROM CarToSellTempStorage car WHERE car.vin = :vin", CarToSellTempStorageEntity.class)
                    .setParameter("vin", vin)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return carToSellTempStorage;
    }

    @Override
    public List<CarToSellTempStorageEntity> findAllCarsToSellTempStorage() {
        List<CarToSellTempStorageEntity> carToSellTempStorageEntity;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carToSellTempStorageEntity = session.createQuery("FROM CarToSellTempStorage",
                    CarToSellTempStorageEntity.class).getResultList();
            transaction.commit();
        }
        return carToSellTempStorageEntity;
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
    public void deleteCarToSellTempStorage(CarToSellTempStorageEntity carToSellTempStorageEntity) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.remove(carToSellTempStorageEntity);
            transaction.commit();
        }
    }

}