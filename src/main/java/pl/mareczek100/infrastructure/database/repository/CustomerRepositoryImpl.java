package pl.mareczek100.infrastructure.database.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.Customer;
import pl.mareczek100.service.dao.CustomerRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public Optional<Customer> findCustomer(String email) {
        Optional<Customer> customer;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            customer = session.createQuery
                            ("FROM Customer cus WHERE cus.email = :email", Customer.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return customer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> customerList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            customerList = session.createQuery("FROM Customer", Customer.class).getResultList();
            transaction.commit();
        }
        return customerList;
    }

    @Override
    public void insertCustomer(Customer customer) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            if (Objects.isNull(customer.getCustomerId())) {
                session.merge(customer);
            }

            session.flush();
            transaction.commit();
        }
    }




}
