package pl.mareczek100.infrastructure.configuration.security.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mareczek100.infrastructure.configuration.security.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUserName(String userName);
}
