package pl.mareczek100.infrastructure.database.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.Part;
import pl.mareczek100.service.dao.PartRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class PartRepositoryImpl implements PartRepository {
    @Override
    public void partInit(Part part) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.persist(part);
            transaction.commit();
        }
    }

    @Override
    public Optional<Part> findPart(String serialNumber) {
        Optional<Part> part;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            part = session.createQuery
                            ("FROM Part pr WHERE pr.serialNumber = :serialNumber", Part.class)
                    .setParameter("serialNumber", serialNumber)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return part;
    }

    @Override
    public List<Part> findAllParts() {
        List<Part> partsList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            partsList = session.createQuery("FROM Part", Part.class).getResultList();
            transaction.commit();
        }
        return partsList;
    }

}
