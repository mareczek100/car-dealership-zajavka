package pl.mareczek100.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mareczek100.api.dto.SalesmanDTO;
import pl.mareczek100.api.dto.dtomapper.SalesmanDtoMapper;
import pl.mareczek100.service.SalesmanService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/salesmen")
@RequiredArgsConstructor
public class SalesmanController {

    private final SalesmanService salesmanService;
    private final SalesmanDtoMapper salesmanDtoMapper;


    @GetMapping
    public String homePage(Model model) {
        List<SalesmanDTO> salesmanDTOs = salesmanService.findAllSalesman().stream()
                .map(salesmanDtoMapper::mapToDTO)
                .toList();
        AtomicInteger id = new AtomicInteger(1);

        salesmanDTOs.forEach(salesmanDTO ->
                model.addAttribute("salesman" + id.getAndIncrement(), salesmanDTO));

        return "salesmen";
    }


}