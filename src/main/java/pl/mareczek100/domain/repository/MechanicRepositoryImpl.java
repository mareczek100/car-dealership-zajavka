package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.Mechanic;
import pl.mareczek100.infrastructure.database.entityMapper.MechanicEntityMapper;
import pl.mareczek100.infrastructure.database.jpaRepository.MechanicJpaRepository;
import pl.mareczek100.service.dao.MechanicRepository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class MechanicRepositoryImpl implements MechanicRepository {

    private final MechanicJpaRepository mechanicJpaRepository;
    private final MechanicEntityMapper mechanicEntityMapper;

    @Override
    public Optional<Mechanic> findMechanic(String pesel) {
        return mechanicJpaRepository.findByPesel(pesel)
                .map(mechanicEntityMapper::mapFromEntity);
    }

    @Override
    public List<Mechanic> findAllMechanics() {
        return mechanicJpaRepository.findAll().stream()
                .map(mechanicEntityMapper::mapFromEntity)
                .toList();
    }

}
