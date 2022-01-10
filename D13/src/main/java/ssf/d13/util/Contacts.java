package ssf.d13.util;

import java.io.File;
import java.io.IOException;
import java.util.HexFormat;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import ssf.d13.model.Contact;

public class Contacts {

    public static void saveContact(Contact contact) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String dataDir = Butler.getDataDir();
            if (dataDir.charAt(dataDir.length() - 1) != '/') {
                dataDir = dataDir + "/";
            }
            String savePath = dataDir + contact.getId() + ".json";

            Butler.log("Writing", savePath);
            objectMapper.writeValue(new File(savePath), contact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateId() {
        Random random = new Random();
        HexFormat hex = HexFormat.of().withUpperCase();
        return hex.toHexDigits(random.nextInt());
    }

    public static Contact generateContact() {
        return new Contact(generateName(), generateEmail(), generatePhoneNumber());
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
    // public void saveContact(Contact contact, Model model, ApplicationArguments
    // applicationArguments) {
    // String dataFilename = contact.getId();
    // Set<String> optnames = applicationArguments.getOptionNames();
    // String[] optnamesArr = optnames.toArray(new String[optnames.size()]);
    // List<String> optValues =
    // applicationArguments.getOptionValues(optnamesArr[0]);
    // String[] optValuesArr = optValues.toArray(new String[optValues.size()]);
    // PrintWriter printWriter = null;
    // try {
    // FileWriter fileWriter = new FileWriter(optValuesArr[0] + "/" + dataFilename,
    // Charset.forName("utf-8"));
    // printWriter = new PrintWriter(fileWriter);
    // printWriter.println(contact.getName());
    // printWriter.println(contact.getEmail());
    // printWriter.println(contact.getPhoneNumber());
    // } catch (IOException e) {
    // logger.error(e.getMessage());
    // } finally {
    // printWriter.close();
    // }

    // model.addAttribute("contact", new Contact(contact.getName(),
    // contact.getEmail(),
    // contact.getPhoneNumber()));
    // }

    // public void getContactById(Model model, String contactId,
    // ApplicationArguments applicationArguments) {
    // Set<String> optnames = applicationArguments.getOptionNames();
    // String[] optnamesArr = optnames.toArray(new String[optnames.size()]);
    // List<String> optValues =
    // applicationArguments.getOptionValues(optnamesArr[0]);
    // String[] optValuesArr = optValues.toArray(new String[optValues.size()]);
    // Contact cResp = new Contact();
    // try {
    // Path filePath = new File(optValuesArr[0] + '/' + contactId).toPath();
    // Charset charset = Charset.forName("utf-8");
    // List<String> stringList = Files.readAllLines(filePath, charset);
    // cResp.setName(stringList.get(0));
    // cResp.setEmail(stringList.get(1));
    // cResp.setPhoneNumber(Integer.parseInt(stringList.get(2)));
    // } catch (IOException e) {
    // logger.error(e.getMessage());
    // }
    // model.addAttribute("contact", cResp);
    // }
}
