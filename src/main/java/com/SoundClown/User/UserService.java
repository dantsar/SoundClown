package com.SoundClown.User;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;
    // Removed autowired here : might not work anymore
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        Iterable users = this.userRepository.findAll();
        List userList = new ArrayList<>();
        users.forEach(user->{userList.add(user);});
        return userList;
    }


    public User create_user(String user_name, String password) {
        User user = new User();
        user.set_user_name(user_name);
        user.set_password(password);
        System.out.println(user);
        this.userRepository.save(user);
        return user;
    }
    
    public boolean update_user(User user) {
        this.userRepository.save(user);
        return true;
    }

    public void delete_user(Long user_id){
        this.userRepository.deleteById(user_id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

}
