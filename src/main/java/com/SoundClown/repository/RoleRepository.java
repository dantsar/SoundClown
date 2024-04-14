package com.SoundClown.repository;

import com.SoundClown.models.Role;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByAuthority(String authority);
}
