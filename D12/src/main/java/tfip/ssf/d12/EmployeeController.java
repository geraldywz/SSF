package tfip.ssf.d12;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {

    @GetMapping("/employee")
    public String employee(Model model) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Ramesh", "Fadatare", "ramesh@gmail.com"));
        employees.add(new Employee("John", "Cena", "john@gmail.com"));
        employees.add(new Employee("Tom", "Cruise", "tom@gmail.com"));
        employees.add(new Employee("Tony", "Stark", "tony@gmail.com"));
        model.addAttribute("employees", employees);
        return "employee";
    }
}
