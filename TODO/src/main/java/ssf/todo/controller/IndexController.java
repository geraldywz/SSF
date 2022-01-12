package ssf.todo.controller;

import java.io.Console;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ssf.todo.model.Todo;
import ssf.todo.util.TodoList;

@Controller
@RequestMapping(path = { "/", "task", "/index.html" })
public class IndexController {

    @GetMapping(produces = { "text/html" })
    public String index(Model model) {
        model.addAttribute("todoList", TodoList.getAll());
        model.addAttribute("todo", TodoList.generateTodo());
        return "index";
    }

    @PostMapping(produces = { "text/html" })
    public String addTask(@ModelAttribute Todo todo, Model model) {
        TodoList.addTodo(todo);
        model.addAttribute("todoList", TodoList.getAll());
        model.addAttribute("todo", TodoList.generateTodo());
        return "index";
    }
}
