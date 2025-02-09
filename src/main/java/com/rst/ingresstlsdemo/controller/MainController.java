package com.rst.ingresstlsdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Value("${app.cars-list}")
    private List<String> carsList;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/cars")
    public String cars(Model model) {
        model.addAttribute("carsList", carsList);
        return "car_list";
    }
}
