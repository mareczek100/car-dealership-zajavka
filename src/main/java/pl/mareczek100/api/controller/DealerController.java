package pl.mareczek100.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/car_dealer")
@RequiredArgsConstructor
public class DealerController {


    @GetMapping
    public String homePage() {

        return "car_dealer";
    }
}