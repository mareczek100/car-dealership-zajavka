package pl.mareczek100.domain.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.MechanicEntity;
import pl.mareczek100.service.dao.MechanicRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class MechanicRepositoryImpl implements MechanicRepository {

    @Override
    public void mechanicInit(MechanicEntity mechanicEntity) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.persist(mechanicEntity);
            transaction.commit();
        }
    }

    @Override
    public Optional<MechanicEntity> findMechanic(String pesel) {
        Optional<MechanicEntity> mechanic;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            mechanic = session.createQuery
                            ("FROM Mechanic mech WHERE mech.pesel = :pesel", MechanicEntity.class)
                    .setParameter("pesel", pesel)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return mechanic;
    }

    @Override
    public List<MechanicEntity> findAllMechanics() {
        List<MechanicEntity> mechanicEntityList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            mechanicEntityList = session.createQuery("FROM Mechanic", MechanicEntity.class).getResultList();
            transaction.commit();
        }
        return mechanicEntityList;
    }

}
