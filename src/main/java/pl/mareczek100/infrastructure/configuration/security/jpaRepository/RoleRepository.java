package pl.mareczek100.infrastructure.configuration.security.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.configuration.security.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}
