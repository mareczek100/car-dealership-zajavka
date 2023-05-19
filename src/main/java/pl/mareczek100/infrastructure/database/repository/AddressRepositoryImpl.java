package pl.mareczek100.infrastructure.database.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.Address;
import pl.mareczek100.service.dao.AddressRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AddressRepositoryImpl implements AddressRepository {

    public Optional<Address> findCustomerAddress(String email) {
        Optional<Address> address;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            address = session.createQuery
                            ("SELECT cus.address FROM Customer cus WHERE cus.email = :email", Address.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return address;
    }

    @Override
    public List<Address> findAllAddresses() {
        List<Address> addressList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            addressList = session.createQuery("FROM Address", Address.class).getResultList();
            transaction.commit();
        }
        return addressList;
    }

    @Override
    public void insertAddress(Address address) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            if (Objects.isNull(address.getAddressId())) {
                session.persist(address);
            }
            session.flush();
            transaction.commit();
        }
    }
}