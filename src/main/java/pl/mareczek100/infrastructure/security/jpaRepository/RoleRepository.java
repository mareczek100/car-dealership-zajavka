package pl.mareczek100.infrastructure.security.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mareczek100.infrastructure.security.RoleEntity;
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}
