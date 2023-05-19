package pl.mareczek100.infrastructure.database.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.CarServiceRequest;
import pl.mareczek100.service.dao.CarServiceRequestRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class CarServiceRequestRepositoryImpl implements CarServiceRequestRepository {


    @Override
    public void insertCarServiceRequest(CarServiceRequest carServiceRequest) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.merge(carServiceRequest);
            session.flush();
            transaction.commit();
        }
    }

    @Override
    public Optional<CarServiceRequest> findCarServiceRequestsByCarVin(String vin) {
        Optional<CarServiceRequest> carServiceRequest;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carServiceRequest = session.createQuery
                            ("FROM CarServiceRequest csr WHERE csr.carToService.vin = :vin",
                                    CarServiceRequest.class)
                    .setParameter("vin", vin)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return carServiceRequest;
    }

    @Override
    public List<CarServiceRequest> findAllCarServiceRequest() {
        List<CarServiceRequest> carServiceRequest;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            carServiceRequest = session.createQuery("FROM CarServiceRequest", CarServiceRequest.class).getResultList();
            transaction.commit();
        }
        return carServiceRequest;
    }
}
