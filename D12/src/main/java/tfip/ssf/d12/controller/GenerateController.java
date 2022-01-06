package tfip.ssf.d12.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import tfip.ssf.d12.model.Generate;

@Controller
public class GenerateController {

    @GetMapping("/generate")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Generate(2));
        return "generate";
    }

    @PostMapping("/generate")
    public String greetingSubmit(@ModelAttribute Generate gen, Model model) {
        model.addAttribute("greeting", gen);
        return "generate";
    }

}
