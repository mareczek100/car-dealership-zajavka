package pl.mareczek100.domain.repository;

import lombok.Value;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.ServiceEntity;
import pl.mareczek100.service.dao.ServiceRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Value
@Repository
public class ServiceRepositoryImpl implements ServiceRepository {
    @Override
    public void serviceInit(ServiceEntity serviceEntity) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.persist(serviceEntity);
            transaction.commit();
        }
    }
    @Override
    public List<ServiceEntity> findAllServices() {
        List<ServiceEntity> serviceEntityList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            serviceEntityList = session.createQuery("FROM Service", ServiceEntity.class).getResultList();
            transaction.commit();
        }
        return serviceEntityList;
    }

    @Override
    public Optional<ServiceEntity> findService(String serviceCode) {
        Optional<ServiceEntity> service;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            service = session.createQuery
                            ("FROM Service ser WHERE ser.serviceCode = :serviceCode", ServiceEntity.class)
                    .setParameter("serviceCode", serviceCode)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return service;
    }

}
