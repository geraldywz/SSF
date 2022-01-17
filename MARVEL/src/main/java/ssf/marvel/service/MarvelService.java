package ssf.marvel.service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.JsonObject;

import static ssf.marvel.util.Constants.*;

@Service
public class MarvelService {

    private final Logger logger = LoggerFactory.getLogger(MarvelService.class);

    private final String privateKey;
    private final String publicKey;

    private final Charset UTF_8 = StandardCharsets.UTF_8;
    private final String OUTPUT_FORMAT = "%-20s:%s";

    @Autowired
    private ApiService api;

    public MarvelService() {
        privateKey = KEY_PRIVATEKEY;
        publicKey = KEY_PUBLICKEY;
    }

    public JsonObject getCharacter(String character) {

        String timeStamp = getTimeStamp();

        final String url = UriComponentsBuilder
                .fromUriString(URL_CHARACTER)
                .queryParam("name", character.trim().replace(" ", "+"))
                .queryParam("ts", timeStamp)
                .queryParam("apikey", publicKey)
                .queryParam("hash", generateMD5Hash(timeStamp))
                .toUriString();

        final JsonObject jsonObject = api.getJsonObject(url);
        return jsonObject;
    }

    private String generateMD5Hash(String timeStamp) {
        String food = timeStamp.concat(privateKey).concat(publicKey);
        logger.info("DIGEST >>>>> " + food);
        String hash = bytesToHex(digest(food.getBytes(UTF_8)));
        logger.info("HASH >>>>> " + hash);
        return hash;
    }

    private String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        return sdf.format(new Timestamp(System.currentTimeMillis()));
    }

    private byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
