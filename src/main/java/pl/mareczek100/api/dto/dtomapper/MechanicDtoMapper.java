package pl.mareczek100.api.dto.dtomapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.api.dto.MechanicDTO;
import pl.mareczek100.domain.Mechanic;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MechanicDtoMapper {

    MechanicDTO mapToDTO(Mechanic mechanic);

}
