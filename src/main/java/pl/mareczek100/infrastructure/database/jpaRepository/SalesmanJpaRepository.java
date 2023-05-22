package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.SalesmanEntity;

import java.util.List;
import java.util.Optional;


public interface SalesmanJpaRepository extends JpaRepository<SalesmanEntity, Integer> {
    Optional<SalesmanEntity> findByPesel(String pesel);

}