package pl.mareczek100.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mareczek100.api.dto.CarDTO;
import pl.mareczek100.api.dto.dtomapper.CarDtoMapper;
import pl.mareczek100.domain.CarToSellTempStorage;
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
        List<CarDTO> allAvailableCars = carToSellService.findAllAvailableCarsToSell().stream()
                .map(carDtoMapper::mapToDTO)
                .toList();
        model.addAttribute("carAvailableDTOs", allAvailableCars);
        return "car";
    }

    @PostMapping("/purchase")
    public String purchase(
            @RequestParam(value = "vin") String vin
    ) {





        return "redirect:/purchase";
    }


}