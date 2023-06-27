package pl.mareczek100.api.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mareczek100.api.dto.SalesmanDTO;
import pl.mareczek100.api.dto.dtomapper.SalesmanDtoMapper;
import pl.mareczek100.service.SalesmanService;

import java.util.List;

@RestController
@RequestMapping("/salesmen")
@RequiredArgsConstructor
public class SalesmanRestController {

    private final SalesmanService salesmanService;
    private final SalesmanDtoMapper salesmanDtoMapper;


    @GetMapping
    public List<SalesmanDTO> homePage() {

        return salesmanService.findAllSalesman().stream()
                .map(salesmanDtoMapper::mapToDTO)
                .toList();
    }

}