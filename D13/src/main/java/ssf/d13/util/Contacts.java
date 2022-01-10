package ssf.d13.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import ssf.d13.model.Contact;

public final class Contacts {

    public static void saveContact(Contact contact) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String savePath = Butler.getDataDir() + contact.getId() + Butler.getJSONExt();
            Butler.log("Writing", savePath);
            objectMapper.writeValue(new File(savePath), contact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<List<Contact>> getAddressBook() {
        List<Contact> addressBook = new ArrayList<>();
        Path path = Paths.get(Butler.getDataDir());
        File dir = path.toFile();
        String[] jsonFiles = dir.list((d, s) -> {
            return s.toLowerCase().endsWith(Butler.getJSONExt());
        });
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            for (String file : jsonFiles) {
                Contact contact = objectMapper.readValue(new File(Butler.getDataDir() + file), Contact.class);
                addressBook.add(contact);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.of(addressBook);
    }

    public static synchronized String generateId() {
        Random random = new Random();
        HexFormat hex = HexFormat.of().withUpperCase();
        String newID = hex.toHexDigits(random.nextInt());
        while (checkDuplicateID(newID)) {
            newID = hex.toHexDigits(random.nextInt());
        }
        return newID;
    }

    public static Contact generateContact() {
        return new Contact(generateId(), generateName(), generateEmail(), generatePhoneNumber());
    }

    private static boolean checkDuplicateID(String ID) {
        boolean duplicateFound = false;
        Path path = Paths.get(Butler.getDataDir());
        File dir = path.toFile();
        String[] jsonFiles = dir.list((d, s) -> {
            return s.endsWith(Butler.getJSONExt());            
        });
        for (String fileName : jsonFiles) {
            fileName = fileName.replace(Butler.getJSONExt(), "");
            // Butler.log(fileName);
            if (ID.equals(fileName)) {
                duplicateFound = true;
                break;
            }
        }
        return duplicateFound;
    }

    private static String generateName() {
        return generateString(4, 6) + " " + generateString(6, 8);
    }

    private static String generateEmail() {
        return generateString(4, 6) + "@" + generateString(6, 8) + ".com";
    }

    private static int generatePhoneNumber() {
        Random random = new Random();
        return random.nextInt(98761234 - 80001234) + 80001234;
    }

    private static String generateString(int min, int max) {
        Random random = new Random();
        int length = random.nextInt(max - min) + min;
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}