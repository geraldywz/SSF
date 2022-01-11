package ssf.d13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ssf.d13.model.Contact;
import ssf.d13.util.Contacts;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String contactForm(Model model) {
        model.addAttribute("contact", Contacts.generateContact());
        model.addAttribute("addressBook", Contacts.getAddressBook().get());
        return "contact";
    }

    @PostMapping("/contact")
    public String greetingSubmit(@ModelAttribute Contact contact, Model model) {
        Contacts.saveContact(contact);
        model.addAttribute("contact", Contacts.generateContact());
        model.addAttribute("addressBook", Contacts.getAddressBook().get());
        return "contact";
    }
}
