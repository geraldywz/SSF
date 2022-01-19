package ssf.openlibrary.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class ApiService {

    private final Logger logger = LoggerFactory.getLogger(ApiService.class);

    public JsonObject getJsonObject(String url) {
        logger.info("URL >>>>> " + url);

        final RequestEntity<Void> requestEntity = RequestEntity.get(url).build();
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new IllegalArgumentException(
                    "Error: status code %s".formatted(responseEntity.getStatusCode().toString()));
        final String body = responseEntity.getBody();

        // logger.info("Payload: %s".formatted(body));

        try (InputStream is = new ByteArrayInputStream(body.getBytes())) {

            return Json.createReader(is).readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}