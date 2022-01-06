package tfip.ssf.d12.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    @GetMapping
    public String weather(@RequestParam(required=true) String city, @RequestParam(name="units", defaultValue="metrics") String units, Model model){
        return "weather";
    }

}
