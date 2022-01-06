package ssf.d12.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ssf.d12.model.Generate;

@Controller
public class GenerateController {

    @GetMapping("/generate")
    public String greetingForm(Model model) {
        model.addAttribute("generate", new Generate());
        return "generate";
    }

    @PostMapping("/generate")
    public String greetingSubmit(@ModelAttribute Generate gen, Model model) {
        model.addAttribute("randomNumbers", generate(gen.getValue()));
        return "generate";
    }

    private List<String> generate(Integer genValue) {
        List<Integer> numbers = new ArrayList<>();
        int index = 1;
        while (index <= 30) {
            numbers.add(index);
            index++;
        }
        Collections.shuffle(numbers);

        List<String> randomNumbers = new ArrayList<>();
        while (randomNumbers.size() < genValue) {
            randomNumbers.add(numbers.get(randomNumbers.size()).toString());
        }
        return randomNumbers;
    }

}
