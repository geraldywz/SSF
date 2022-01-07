package ssf.d13.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ssf.d13.model.Contact;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String contactForm(Model model) {
        model.addAttribute("contact", genContact());
        return "contact";
    }

    @PostMapping("/contact")
    public String greetingSubmit(@ModelAttribute Contact contact, Model model) {
        // model.addAttribute("randomNumbers", ));
        return "generate";
    }

    private Contact genContact() {
        Contact contact = new Contact();
        contact.setName(genString(6));
        contact.setEmail(genString(6) + "@" + genString(8) + ".com");
        contact.setPhoneNumber(genPhoneNumber());
        return contact;
    }

    private String genString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    private int genPhoneNumber() {
        Random random = new Random();
        return random.nextInt(19999999) + 80000000;
    }
}
