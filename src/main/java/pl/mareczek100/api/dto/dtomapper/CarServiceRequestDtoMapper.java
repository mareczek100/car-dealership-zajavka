package pl.mareczek100.api.dto.dtomapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.api.dto.CarServiceRequestDTO;
import pl.mareczek100.domain.CarServiceRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarServiceRequestDtoMapper {

    CarServiceRequestDTO mapToDTO(CarServiceRequest carServiceRequest);
    CarServiceRequest mapFromDTO(CarServiceRequestDTO carServiceRequestDTO);

}
