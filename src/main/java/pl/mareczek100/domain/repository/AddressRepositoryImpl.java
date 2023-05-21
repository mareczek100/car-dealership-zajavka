package pl.mareczek100.domain.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.AddressEntity;
import pl.mareczek100.service.dao.AddressRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AddressRepositoryImpl implements AddressRepository {

    public Optional<AddressEntity> findCustomerAddress(String email) {
        Optional<AddressEntity> address;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            address = session.createQuery
                            ("SELECT cus.address FROM Customer cus WHERE cus.email = :email", AddressEntity.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
            transaction.commit();
        }
        return address;
    }

    @Override
    public List<AddressEntity> findAllAddresses() {
        List<AddressEntity> addressEntityList;
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            addressEntityList = session.createQuery("FROM Address", AddressEntity.class).getResultList();
            transaction.commit();
        }
        return addressEntityList;
    }

    @Override
    public void insertAddress(AddressEntity addressEntity) {
        try (Session session = HibernateConfig.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();
            if (Objects.isNull(addressEntity.getAddressId())) {
                session.persist(addressEntity);
            }
            session.flush();
            transaction.commit();
        }
    }
}