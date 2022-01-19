package ssf.openlibrary.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Book {

    private String key;
    private String title;
    private String excerpt;
    private String description;
    private boolean cached;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return this.excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCached() {
        return this.cached;
    }

    public boolean getCached() {
        return this.cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public static Book create(JsonObject o) {
        final Book b = new Book();

        if (o.containsKey("key")) {
            b.setKey(o.getString("key").replace("/works/", ""));
        }

        if (o.containsKey("title")) {
            try {
                b.setTitle(o.getString("title"));
            } catch (Exception e) {
                b.setTitle("Not available.");
            }
        }

        if (o.containsKey("description")) {
            try {
                b.setDescription(o.getString("description"));
            } catch (Exception e) {
                b.setDescription("Not available.");
            }
        } else {
            b.setDescription("Not available.");
        }

        if (o.containsKey("excerpt")) {
            try {
                b.setExcerpt(o.getString("excerpt"));
            } catch (Exception e) {
                b.setExcerpt("Not available.");
            }
        } else {
            b.setExcerpt("Not available.");
        }

        return b;
    }

    public static Book create(String jsonString) {
        try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
            final JsonReader reader = Json.createReader(is);
            return create(reader.readObject());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Book();
    }

    @Override
    public String toString() {
        return "key: %s, title: %s, description: %s, excerpt: %s"
                .formatted(key, title, description, excerpt);
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("key", key)
                .add("title", title)
                .add("description", description)
                .add("excerpt", excerpt)
                .build();
    }
}