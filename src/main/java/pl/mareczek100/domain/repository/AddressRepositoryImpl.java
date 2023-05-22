package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.Address;
import pl.mareczek100.infrastructure.configuration.HibernateConfig;
import pl.mareczek100.infrastructure.database.entity.AddressEntity;
import pl.mareczek100.infrastructure.database.jpaRepository.AddressJpaRepository;
import pl.mareczek100.service.dao.AddressRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private final AddressJpaRepository addressJpaRepository;
    private final AddressEntityMapper addressEntityMapper;
    public Optional<Address> findCustomerAddress(String email) {
    return addressJpaRepository.findCustomerAddress(email)
             .map(addressEntity -> mapFromEntity(addressEntity));
    }
    @Override
    public List<Address> findAllAddresses() {
        return addressJpaRepository.findAll().stream()
                .map(addressJpaRepository::mapFromEntity)
                .toList();
    }

}