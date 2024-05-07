package SoundClown;

import SoundClown.AudioPlayer.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AudioStoragePropertiesTest {

    @Test
    void setLocation() {
        AudioStorageProperties properties = new AudioStorageProperties();
        String location = "/test/location";
        properties.setLocation(location);
        assertEquals(location, properties.getLocation());
    }

    @Test
    void getLocation() {
        AudioStorageProperties properties = new AudioStorageProperties();
        String location = "/test/location";
        properties.setLocation(location);
        assertEquals(location, properties.getLocation());
    }
}