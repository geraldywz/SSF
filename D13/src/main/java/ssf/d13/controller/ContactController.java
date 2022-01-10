package ssf.d13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ssf.d13.model.Contact;
import ssf.d13.util.Butler;
import ssf.d13.util.Contacts;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String contactForm(Model model) {
        Contact newContact = Contacts.generateContact();
        Butler.log("GET",newContact.getId());
        model.addAttribute("contact", newContact);
        model.addAttribute("addressBook", Contacts.getAddressBook().get());
        
        return "contact";
    }

    @PostMapping("/contact")
    public String greetingSubmit(@ModelAttribute Contact contact, Model model) {
        Butler.log("POST Args",contact.getId());
        Contacts.saveContact(contact);
        Contact newContact = Contacts.generateContact();
        Butler.log("POST",newContact.getId());
        model.addAttribute("contact", newContact);
        model.addAttribute("addressBook", Contacts.getAddressBook().get());
        return "contact";
    }
}
