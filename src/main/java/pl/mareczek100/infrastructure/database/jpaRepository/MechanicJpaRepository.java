package pl.mareczek100.infrastructure.database.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.database.entity.MechanicEntity;

import java.util.List;
import java.util.Optional;


public interface MechanicJpaRepository extends JpaRepository<MechanicEntity, Integer> {
    Optional<MechanicEntity> findByPesel(String pesel);

}
