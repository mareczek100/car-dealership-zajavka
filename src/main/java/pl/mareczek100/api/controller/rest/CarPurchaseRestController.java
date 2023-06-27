package pl.mareczek100.api.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.mareczek100.api.dto.CarDTO;
import pl.mareczek100.api.dto.InvoiceDTO;
import pl.mareczek100.api.dto.dtomapper.CarDtoMapper;
import pl.mareczek100.api.dto.dtomapper.InvoiceDtoMapper;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.service.CarToSellService;
import pl.mareczek100.service.PurchaseCarService;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarPurchaseRestController {

    private final PurchaseCarService purchaseCarService;
    private final CarToSellService carToSellService;
    private final InvoiceDtoMapper invoiceDtoMapper;
    private final CarDtoMapper carDtoMapper;

    @GetMapping
    public List<CarDTO> homePage() {

        return carToSellService.findAllAvailableCarsToSell().stream()
                .map(carDtoMapper::mapToDTO)
                .toList();
    }

    @GetMapping("/purchase/{carVin}")
    public CarDTO purchaseView(
            @PathVariable String carVin
    ) {
        return carDtoMapper.mapToDTO(carToSellService.findAvailableCarToSell(carVin));
    }

    @PostMapping("/purchase/{carVin}")
    public InvoiceDTO buyCar(
            @PathVariable String carVin,
            @Valid @RequestParam(name = "email") String email
    ) {
        Invoice invoice = purchaseCarService.buyANewCar(carVin, email);
        return invoiceDtoMapper.mapToDTO(invoice);
    }

}