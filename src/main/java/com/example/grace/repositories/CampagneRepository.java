package com.example.grace.repositories;

import com.example.grace.entities.Campagne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampagneRepository extends JpaRepository<Campagne, Long> {
}
