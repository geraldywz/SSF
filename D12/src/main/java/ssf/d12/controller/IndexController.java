package ssf.d12.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = { "/", "/index.html" })
public class IndexController {

	@GetMapping(produces = { "text/html" })
	public String index(Model model) {
		Calendar cal = Calendar.getInstance();
		model.addAttribute("currTime", (new Date()).toString());
		model.addAttribute("currHour", cal.get(Calendar.HOUR_OF_DAY));
		return "index";
	}
}
