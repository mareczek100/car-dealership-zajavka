package pl.mareczek100.api.dto.dtomapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.mareczek100.api.dto.InvoiceDTO;
import pl.mareczek100.api.dto.SalesmanDTO;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.domain.Salesman;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvoiceDtoMapper {

    InvoiceDTO mapToDTO(Invoice invoice);

}
