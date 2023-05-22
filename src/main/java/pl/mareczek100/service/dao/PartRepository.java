package pl.mareczek100.service.dao;

import pl.mareczek100.domain.Part;

import java.util.List;
import java.util.Optional;


public interface PartRepository {

    Optional<Part> findPart(String serialNumber);

    List<Part> findAllParts();

}
