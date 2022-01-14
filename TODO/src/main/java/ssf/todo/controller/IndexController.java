package ssf.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ssf.todo.model.Todo;
import ssf.todo.service.Taskmaster;

@Controller
@RequestMapping(path = {"/", "/index.html"})
public class IndexController {

    @GetMapping(produces = { "text/html" })
    public String index(Model model) {
        Taskmaster.generateUsers();
        model.addAttribute("usernames", Taskmaster.getUsernames());
        return "index";
    }

    @PostMapping(produces = { "text/html" })
    public String addTask(@ModelAttribute Todo todo, Model model) {
        
        return "index";
    }
}
