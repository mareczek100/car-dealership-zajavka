package pl.mareczek100.domain.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequestEntity;
import pl.mareczek100.service.dao.CarServiceRequestRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class CarServiceRequestRepositoryImpl implements CarServiceRequestRepository {


    @Override
    public void insertCarServiceRequest(CarServiceRequestEntity carServiceRequestEntity) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.merge(carServiceRequestEntity);
            session.flush();
            transaction.commit();
        }
    }

    @Override
    public Optional<CarServiceRequestEntity> findCarServiceRequestsByCarVin(String vin) {
        Optional<CarServiceRequestEntity> carServiceRequest;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carServiceRequest = session.createQuery
                            ("FROM CarServiceRequest csr WHERE csr.carToService.vin = :vin",
                                    CarServiceRequestEntity.class)
                    .setParameter("vin", vin)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return carServiceRequest;
    }

    @Override
    public List<CarServiceRequestEntity> findAllCarServiceRequest() {
        List<CarServiceRequestEntity> carServiceRequestEntity;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carServiceRequestEntity = session.createQuery("FROM CarServiceRequest", CarServiceRequestEntity.class).getResultList();
            transaction.commit();
        }
        return carServiceRequestEntity;
    }
}
