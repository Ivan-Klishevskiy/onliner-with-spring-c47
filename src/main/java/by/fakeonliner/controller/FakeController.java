package by.fakeonliner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FakeController {

    @GetMapping
    public String index(){
        return "index";
    }
}
