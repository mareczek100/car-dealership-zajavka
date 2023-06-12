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
@RequestMapping("/mechanics")
@RequiredArgsConstructor
public class MechanicController {

    @GetMapping
    public String serviceRequest() {

        return "mechanics";
    }

    @PostMapping
    public String createServiceRequestByVin(

    ) {

        return "mechanics";
    }
}