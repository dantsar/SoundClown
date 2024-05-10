package SoundClown;

import SoundClown.Track.Track;
import SoundClown.User.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void test_getAllUsers() {
        List<User> mockUsers = Arrays.asList(
                new User(1L, "Andy", "123"),
                new User(2L, "Fred", "456")
        );

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> allUsers = userService.getAllUsers();

        Assertions.assertFalse(allUsers.isEmpty());
    }

    @Test
    public void test_create_users() {
        User user = new User();
        user.set_user_name("Andy");
        user.set_password("123");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User savedUser = userService.create_user("Andy", "123");

        Assertions.assertEquals(savedUser.get_user_name(), user.get_user_name());
        Assertions.assertEquals(savedUser.get_password(), user.get_password());
    }

    @Test
    public void test_update_users() {
        User user = new User();
        user.set_user_name("Andy");
        user.set_password("123");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        boolean userUpdated = userService.update_user(user);

        Assertions.assertTrue(userUpdated);
    }
}