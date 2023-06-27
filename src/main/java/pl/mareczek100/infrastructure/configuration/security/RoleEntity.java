package pl.mareczek100.infrastructure.configuration.security;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Value;

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
