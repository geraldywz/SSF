package ssf.d13.service;

import java.util.List;
import java.util.Optional;

import ssf.d13.model.Contact;

public interface ContactRepo {
    public void save(final Contact contact);

    public Contact findById(final String contactId);

    public Optional<List<Contact>> getAddressBook();

}
