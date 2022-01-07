package ssf.d13.model;

import java.util.HexFormat;
import java.util.Random;

public class Contact {
    private String name;
    private String email;
    private String id;
    private Integer phoneNumber;

    public Contact(String name, String email, Integer phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = generateId();
    }

    public Contact() {
        this.id = generateId();
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

    public static String generateId() {
        Random random = new Random();
        HexFormat hex = HexFormat.of();
        hex = hex.withUpperCase();
        return hex.toHexDigits(random.nextInt());
    }
}
