package pl.mareczek100.domain.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.PartEntity;
import pl.mareczek100.service.dao.PartRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class PartRepositoryImpl implements PartRepository {
    @Override
    public void partInit(PartEntity partEntity) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.persist(partEntity);
            transaction.commit();
        }
    }

    @Override
    public Optional<PartEntity> findPart(String serialNumber) {
        Optional<PartEntity> part;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            part = session.createQuery
                            ("FROM Part pr WHERE pr.serialNumber = :serialNumber", PartEntity.class)
                    .setParameter("serialNumber", serialNumber)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return part;
    }

    @Override
    public List<PartEntity> findAllParts() {
        List<PartEntity> partsList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            partsList = session.createQuery("FROM Part", PartEntity.class).getResultList();
            transaction.commit();
        }
        return partsList;
    }

}
