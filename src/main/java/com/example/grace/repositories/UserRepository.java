package com.example.grace.repositories;


import com.example.grace.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByPseudo(String pseudo);

	Boolean existsByPseudo(String pseudo);

	Boolean existsByEmail(String email);

    List<User> findByVilleIgnoreCase(String ville);
}
