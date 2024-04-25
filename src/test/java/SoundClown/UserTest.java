package SoundClown;

import SoundClown.User.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void set_user_id() {
        User user = new User();
        user.set_user_id(1L);
        assertEquals(1L, user.get_user_id());
    }

    @Test
    void set_user_name() {
        User user = new User();
        user.set_user_name("user_name");
        assertEquals("user_name", user.get_user_name());
    }

    @Test
    void set_password() {
        User user = new User();
        user.set_password("password");
        assertEquals("password", user.get_password());
    }

    @Test
    void get_user_id() {
        User user = new User();
        user.set_user_id(1L);
        assertEquals(1L, user.get_user_id());
    }

    @Test
    void get_user_name() {
        User user = new User();
        user.set_user_name("test_user");
        assertEquals("test_user", user.get_user_name());
    }

    @Test
    void get_password() {
        User user = new User();
        user.set_password("password");
        assertEquals("password", user.get_password());
    }

    @Test
    void testToString() {
        User user = new User();
        user.set_user_id(1L);
        user.set_user_name("user_name");
        user.set_password("password");

        String expectedToString = "User{user_id=1, user_name=user_name, password=password'}";
        assertEquals(expectedToString, user.toString());
    }
}