package pl.mareczek100.infrastructure.configuration.security.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.configuration.security.RoleEntity;
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}
