package com.example.grace.config;



import com.example.grace.entities.ERole;
import com.example.grace.entities.Role;
import com.example.grace.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Vérifie si les rôles existent déjà
        for (ERole eRole : ERole.values()) {
            if (!roleRepository.findByName(eRole).isPresent()) {
                Role role = new Role(eRole);
                roleRepository.save(role);
                System.out.println("Role ajouté : " + eRole);
            }
        }
    }
}
