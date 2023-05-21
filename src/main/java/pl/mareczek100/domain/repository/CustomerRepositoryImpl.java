package pl.mareczek100.domain.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.CustomerEntity;
import pl.mareczek100.service.dao.CustomerRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public Optional<CustomerEntity> findCustomer(String email) {
        Optional<CustomerEntity> customer;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            customer = session.createQuery
                            ("FROM Customer cus WHERE cus.email = :email", CustomerEntity.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return customer;
    }

    @Override
    public List<CustomerEntity> findAllCustomers() {
        List<CustomerEntity> customerEntityList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            customerEntityList = session.createQuery("FROM Customer", CustomerEntity.class).getResultList();
            transaction.commit();
        }
        return customerEntityList;
    }

    @Override
    public void insertCustomer(CustomerEntity customerEntity) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            if (Objects.isNull(customerEntity.getCustomerId())) {
                session.merge(customerEntity);
            }

            session.flush();
            transaction.commit();
        }
    }




}
