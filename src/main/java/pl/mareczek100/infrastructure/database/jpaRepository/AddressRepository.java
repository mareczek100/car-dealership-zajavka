package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.AddressEntity;

import java.util.List;
import java.util.Optional;
public interface AddressRepository extends JpaRepository<Object, Integer> {

    Optional<AddressEntity> findCustomerAddress(String email);

    void insertAddress(AddressEntity addressEntity);

    List<AddressEntity> findAllAddresses();

}
