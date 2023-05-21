package pl.mareczek100.domain.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.CarServicePartsEntity;
import pl.mareczek100.service.dao.CarServicePartsRepository;

import java.util.Objects;


@Repository
public class CarServicePartsRepositoryImpl implements CarServicePartsRepository {

    @Override
    public void carServicePartsInit(CarServicePartsEntity carServicePartsEntity) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            if (Objects.nonNull(carServicePartsEntity.getPartEntity())){
                session.persist(carServicePartsEntity);
            }
            session.flush();
            transaction.commit();
        }
    }
}
