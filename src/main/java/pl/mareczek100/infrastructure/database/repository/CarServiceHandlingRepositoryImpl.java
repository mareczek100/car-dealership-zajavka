package pl.mareczek100.infrastructure.database.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.CarServiceHandling;
import pl.mareczek100.service.dao.CarServiceHandlingRepository;

import java.util.Objects;


@Repository
public class CarServiceHandlingRepositoryImpl implements CarServiceHandlingRepository {
    @Override
    public void carServiceHandlingInit(CarServiceHandling carServiceHandling) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.merge(carServiceHandling);
            session.flush();
            transaction.commit();
        }
    }
}
