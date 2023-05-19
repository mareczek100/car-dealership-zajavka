package pl.mareczek100.infrastructure.database.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.Mechanic;
import pl.mareczek100.service.dao.MechanicRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class MechanicRepositoryImpl implements MechanicRepository {

    @Override
    public void mechanicInit(Mechanic mechanic) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.persist(mechanic);
            transaction.commit();
        }
    }

    @Override
    public Optional<Mechanic> findMechanic(String pesel) {
        Optional<Mechanic> mechanic;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            mechanic = session.createQuery
                            ("FROM Mechanic mech WHERE mech.pesel = :pesel", Mechanic.class)
                    .setParameter("pesel", pesel)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return mechanic;
    }

    @Override
    public List<Mechanic> findAllMechanics() {
        List<Mechanic> mechanicList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            mechanicList = session.createQuery("FROM Mechanic", Mechanic.class).getResultList();
            transaction.commit();
        }
        return mechanicList;
    }

}
