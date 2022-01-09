package ssf.d13.model;

import ssf.d13.util.Contacts;

public class Contact {
    private String name;
    private String email;
    private String id;
    private Integer phoneNumber;

    public Contact(String name, String email, Integer phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = Contacts.generateId();
    }

    public Contact() {
        this.id = Contacts.generateId();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = Integer.valueOf(phoneNumber);
    }
}
