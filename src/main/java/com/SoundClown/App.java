package com.SoundClown;

import com.SoundClown.models.ApplicationUser;
import com.SoundClown.models.Role;
import com.SoundClown.repository.RoleRepository;
import com.SoundClown.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class App{
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	// Adds admin user, ADMIN role and USER role on startup
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;

			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			ApplicationUser admin = new ApplicationUser(1, "admin", passwordEncode.encode("password"), roles);

			userRepository.save(admin);
		};
	}
}
