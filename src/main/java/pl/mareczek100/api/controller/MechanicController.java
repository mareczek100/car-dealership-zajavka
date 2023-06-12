package pl.mareczek100.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mareczek100.api.dto.MechanicDTO;
import pl.mareczek100.api.dto.dtomapper.MechanicDtoMapper;
import pl.mareczek100.domain.Invoice;
import pl.mareczek100.service.InvoiceService;
import pl.mareczek100.service.PurchaseCarService;
import pl.mareczek100.service.MechanicService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/mechanics")
@RequiredArgsConstructor
public class MechanicController {

    private final MechanicService mechanicService;
    private final MechanicDtoMapper mechanicDtoMapper;


    @GetMapping
    public String homePage(Model model) {
        List<MechanicDTO> mechanicDTOs = mechanicService.findAllMechanics().stream()
                .map(mechanicDtoMapper::mapToDTO)
                .toList();

        model.addAttribute("mechanicDTOs", mechanicDTOs);

        return "mechanics";
    }
}