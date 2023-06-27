package pl.mareczek100.api.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mareczek100.api.dto.InvoiceDTO;
import pl.mareczek100.api.dto.dtomapper.InvoiceDtoMapper;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.service.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceRestController {

    private final InvoiceService invoiceService;
    private final InvoiceDtoMapper invoiceDtoMapper;


    @GetMapping("/show")
    public ResponseEntity<List<InvoiceDTO>> checkCustomerInvoice(
            @RequestParam("email") String email
    ) {
        List<Invoice> invoiceByEmail = invoiceService.findInvoiceByEmail(email);
        if (invoiceByEmail.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<InvoiceDTO> invoiceDTOs = invoiceByEmail.stream()
                .map(invoiceDtoMapper::mapToDTO)
                .toList();

        return ResponseEntity.ok(invoiceDTOs);
    }

}