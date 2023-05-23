package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.mareczek100.infrastructure.database.entity.AddressEntity;

import java.util.Optional;

public interface AddressJpaRepository extends JpaRepository<AddressEntity, Integer> {
    @Query("SELECT cus.address FROM Customer cus WHERE cus.email = :email")
    Optional<AddressEntity> findCustomerAddress(@Param("email") String email);

}
