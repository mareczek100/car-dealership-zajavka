package pl.mareczek100.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mareczek100.api.dto.CarDTO;
import pl.mareczek100.api.dto.dtomapper.CarDtoMapper;
import pl.mareczek100.service.CarToSellService;
import pl.mareczek100.service.PurchaseCarService;

import java.util.List;

@Controller
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarPurchaseController {

    private final PurchaseCarService purchaseCarService;
    private final CarToSellService carToSellService;
    private final CarDtoMapper carDtoMapper;

    @GetMapping
    public String homePage(Model model) {
        List<CarDTO> allCars = carToSellService.findAllCarsToSell().stream()
                .map(carDtoMapper::mapToDTO)
                .toList();
        List<CarDTO> allAvailableCars = carToSellService.findAllAvailableCarsToSell().stream()
                .map(carDtoMapper::mapToDTO)
                .toList();

        model.addAttribute("availableYes", "YES");
        model.addAttribute("availableNo", "NO");
        model.addAttribute("carDTOs", allCars);
        model.addAttribute("carAvailable", allAvailableCars);

        return "car";
    }

    @GetMapping("/purchase/{carVin}")
    public String purchaseView(
            @PathVariable String carVin, Model model
    ) {
        
        model.addAttribute("carVin", carVin);
        return "purchase";
    }

    @PostMapping("/purchase/{carVin}")
    public String buyCar(
            @PathVariable String carVin,
            @Valid @RequestParam(name = "email") String email,
            Model model
    ) {
        String carBought = "Car [" + carVin + "] is Yours!! Take a ride!:)";
        purchaseCarService.buyANewCar(carVin, email);
        model.addAttribute("carBought", carBought);

        return "purchase";
    }


}