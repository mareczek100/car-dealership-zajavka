package pl.mareczek100.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mareczek100.domain.Address;
import pl.mareczek100.domain.Customer;
import pl.mareczek100.infrastructure.security.jpaRepository.RoleRepository;
import pl.mareczek100.infrastructure.security.jpaRepository.UserRepository;
import pl.mareczek100.service.AddressService;
import pl.mareczek100.service.dao.CustomerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SecurityService {

    private final static int ROLE_SALESMAN = 1;
    private final static int ROLE_MECHANIC = 2;
    private final static int ROLE_CUSTOMER = 3;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public UserEntity insertRole(Customer customer) {

        RoleEntity roleCustomer = roleRepository.findById(ROLE_CUSTOMER).orElseThrow();

        UserEntity userEntity = UserEntity.builder()
                .userName(customer.getName().concat("_").concat(customer.getSurname()))
                .email(customer.getEmail())
                .password("0000")
                .active(true)
                .roles(Set.of(roleCustomer))
                .build();

        return userRepository.saveAndFlush(userEntity);
    }

}
