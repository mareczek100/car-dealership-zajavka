package pl.mareczek100.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mareczek100.api.dto.InvoiceDTO;
import pl.mareczek100.api.dto.dtomapper.InvoiceDtoMapper;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.service.InvoiceService;

import java.util.List;

@Controller
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceDtoMapper invoiceDtoMapper;

    @GetMapping
    public String homePage() {
        return "invoice";
    }

    @GetMapping("/show")
    public String checkCustomerInvoice(
            @RequestParam("email") String email, Model model
    ) {
        List<Invoice> invoiceByEmail = invoiceService.findInvoiceByEmail(email);
        if (invoiceByEmail.isEmpty()) {
            model.addAttribute("noInvoice", "You have no invoices mate!!");
            return "invoice";
        }
        List<InvoiceDTO> invoiceDTOs = invoiceByEmail.stream()
                .map(invoiceDtoMapper::mapToDTO)
                .toList();
        model.addAttribute("invoiceDTOs", invoiceDTOs);

        return "invoice_display";
    }

}