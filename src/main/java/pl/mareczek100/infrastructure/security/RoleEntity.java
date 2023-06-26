package pl.mareczek100.infrastructure.security;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Value;
import pl.mareczek100.infrastructure.database.entity.CustomerEntity;

import java.util.Set;

@Value
@Entity
@Builder
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    Integer roleId;

    @Column(name = "role")
    String role;

}
