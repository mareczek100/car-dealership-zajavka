package pl.mareczek100.api.dto.dtomapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.api.dto.PartDTO;
import pl.mareczek100.domain.Part;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PartDtoMapper {

    PartDTO mapToDTO(Part part);

}
