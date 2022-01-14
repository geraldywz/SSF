package ssf.todo.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ssf.todo.model.Todo;
import ssf.todo.service.Taskmaster;

@Controller
@RequestMapping(path = {"/task"})
public class TaskController {

    private final Logger logger = Logger.getLogger(IndexController.class.getName());

    @GetMapping(produces = { "text/html" })
    public String index(Model model) {
        model.addAttribute("todoList", Taskmaster.getAll());
        model.addAttribute("todo", Taskmaster.generateTodo());
        return "tasks";
    }

    @PostMapping(produces = { "text/html" })
    public String addTask(@ModelAttribute Todo todo, Model model) {
        Taskmaster.addTodo(todo);
        logger.log(Level.INFO, "Task >>>>> "+todo.getTask());
        model.addAttribute("todoList", Taskmaster.getAll());
        model.addAttribute("todo", Taskmaster.generateTodo());
        return "tasks";
    }
}
