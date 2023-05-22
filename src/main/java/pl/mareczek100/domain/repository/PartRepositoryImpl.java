package pl.mareczek100.domain.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mareczek100.domain.Part;
import pl.mareczek100.infrastructure.database.jpaRepository.PartJpaRepository;
import pl.mareczek100.service.dao.PartRepository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class PartRepositoryImpl implements PartRepository {

private final PartJpaRepository partJpaRepository;
private final PartEntityMapper partEntityMapper;
    @Override
    public Optional<Part> findPart(String serialNumber) {
        return partJpaRepository.findPartBySerialNumber(serialNumber)
                .map(partEntityMapper::mapFromEntity);
    }

    @Override
    public List<Part> findAllParts() {
        return partJpaRepository.findAll().stream()
                .map(partEntityMapper::mapFromEntity)
                .toList();
    }

}
