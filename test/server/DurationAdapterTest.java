package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DurationAdapterTest {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

    @Test
    public void testSerializeDuration() {
        Duration duration = Duration.ofHours(1).plusMinutes(30);
        String json = gson.toJson(duration);

        assertEquals("\"PT1H30M\"", json);
    }

    @Test
    public void testDeserializeDuration() {
        String json = "\"PT1H30M\"";
        Duration duration = gson.fromJson(json, Duration.class);

        assertEquals(Duration.ofHours(1).plusMinutes(30), duration);
    }
}