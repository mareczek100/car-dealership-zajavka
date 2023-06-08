package pl.mareczek100.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.service.InvoiceService;
import pl.mareczek100.service.PurchaseCarService;

@Controller
//@RequestMapping("/mechanic")
@RequiredArgsConstructor
public class MechanicController {

//    private final PurchaseCarService purchaseCarService;
//    private final InvoiceService invoiceService;
//    private Invoice invoice;
//
//
//    @GetMapping
//    public String homePage() {
//        return "customer";
//    }
//
//    @GetMapping("/purchase")
//    public String purchase() {
//        return "purchase";
//    }
//    @PostMapping("/purchase")
//    public String purchase(
//            @RequestParam(value = "vin") String vin
//    ) {
//        this.invoice = purchaseCarService.buyANewCar(vin);
//
//        return "redirect:/purchase";
//    }
//
//    @GetMapping("/invoice")
//    public String invoices(Model model) {
//        Invoice invoice = invoiceService.findInvoice(this.invoice.getInvoiceNumber());
//        model.addAttribute("invoice", invoice);
//        return "invoice";
//    }
}