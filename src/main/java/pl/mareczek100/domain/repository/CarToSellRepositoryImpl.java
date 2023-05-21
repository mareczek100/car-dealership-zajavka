package pl.mareczek100.domain.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.CarToSellEntity;
import pl.mareczek100.service.dao.CarToSellRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class CarToSellRepositoryImpl implements CarToSellRepository {
    @Override
    public void carToSellInit(CarToSellEntity carToSellEntity) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.persist(carToSellEntity);
            transaction.commit();
        }
    }

    @Override
    public Optional<CarToSellEntity> findCarToSell(String vin) {
        Optional<CarToSellEntity> carToSell;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carToSell = session.createQuery
                            ("FROM CarToSell car WHERE car.vin = :vin", CarToSellEntity.class)
                    .setParameter("vin", vin)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return carToSell;
    }

    @Override
    public List<CarToSellEntity> findAllCarsToSell() {
        List<CarToSellEntity> carToSellEntity;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carToSellEntity = session.createQuery("FROM CarToSell", CarToSellEntity.class).getResultList();
            transaction.commit();
        }
        return carToSellEntity;
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