package com.SoundClown.repository;

import com.SoundClown.models.ApplicationUser;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {
	ApplicationUser findByUsername(String username);
}
