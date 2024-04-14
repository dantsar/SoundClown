package com.SoundClown.services;

import com.SoundClown.models.Role;
import com.SoundClown.models.ApplicationUser;
import com.SoundClown.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
/*
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
*/
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("In the user details service");
        ApplicationUser tempUser = userRepository.findByUsername(username);
        System.out.println(tempUser.getUsername());
        return userRepository.findByUsername(username);
    }

}
