package com.example.grace.repositories;

import com.example.grace.entities.Campagne;
import com.example.grace.entities.Don;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonRepository extends JpaRepository<Don, Long> {

    List<Don> findByCampagne(Campagne campagne);
}
