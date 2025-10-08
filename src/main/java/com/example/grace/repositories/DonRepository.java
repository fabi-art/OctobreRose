package com.example.grace.repositories;
import com.example.grace.entities.Campagne;
import com.example.grace.entities.Don;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonRepository extends JpaRepository<Don, Long> {
}
