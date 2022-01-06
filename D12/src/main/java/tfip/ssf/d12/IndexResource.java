package tfip.ssf.d12;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = { "/", "/index.html" })
public class IndexResource {

	@GetMapping(produces = { "text/html" })
	public String index(Model model) {
		model.addAttribute("currTime", (new Date()).toString());
		return "index";
	}
}
