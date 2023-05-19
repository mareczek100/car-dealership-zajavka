package pl.mareczek100.infrastructure.database.repository;

import lombok.Value;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.Service;
import pl.mareczek100.service.dao.ServiceRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Value
@Repository
public class ServiceRepositoryImpl implements ServiceRepository {
    @Override
    public void serviceInit(Service service) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            session.persist(service);
            transaction.commit();
        }
    }
    @Override
    public List<Service> findAllServices() {
        List<Service> serviceList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            serviceList = session.createQuery("FROM Service", Service.class).getResultList();
            transaction.commit();
        }
        return serviceList;
    }

    @Override
    public Optional<Service> findService(String serviceCode) {
        Optional<Service> service;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            service = session.createQuery
                            ("FROM Service ser WHERE ser.serviceCode = :serviceCode", Service.class)
                    .setParameter("serviceCode", serviceCode)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return service;
    }

}
