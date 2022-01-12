package ssf.d13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;

import ssf.d13.model.Contact;
import ssf.d13.service.ContactsRedis;
import ssf.d13.util.Contacts;

@Controller
@RequestMapping(path = { "/", "/index.html" })
public class RedisContactController {

    @Autowired
    ContactsRedis service;

    @GetMapping(produces = { "text/html" })
    public String returnIndex(Model model) {
        model.addAttribute("contact", Contacts.generateContact());
        model.addAttribute("addressBook", service.getAddressBook().get());
        return "index";
    }

    @GetMapping("/{contactId}")
    public String getContact(Model model, @PathVariable(value = "contactId") String contactId) {
        Contact contact = service.findById(contactId);
        model.addAttribute("contact", contact);
        return "index";
    }

    @PostMapping(produces = { "text/html" })
    public String contactSubmit(@ModelAttribute Contact contact, Model model) {
        Contact savedContact = new Contact(
                Contacts.generateId(),
                contact.getName(),
                contact.getEmail(),
                contact.getPhoneNumber());
        service.save(savedContact);
        model.addAttribute("contact", Contacts.generateContact());
        model.addAttribute("addressBook", service.getAddressBook().get());
        return "index";
    }
}
