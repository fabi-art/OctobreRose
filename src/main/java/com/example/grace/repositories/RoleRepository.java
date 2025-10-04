package com.example.grace.repositories;

import java.util.Optional;


import com.example.grace.entities.ERole;
import com.example.grace.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
