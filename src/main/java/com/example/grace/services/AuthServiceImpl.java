
package com.example.grace.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.grace.entities.ERole;
import com.example.grace.entities.Role;
import com.example.grace.entities.User;
import com.example.grace.exceptions.CustomException;
import com.example.grace.payload.request.ChangePasswordRequest;
import com.example.grace.payload.request.LoginRequest;
import com.example.grace.payload.request.SignupRequest;
import com.example.grace.payload.response.JwtResponse;
import com.example.grace.payload.response.MessageResponse;
import com.example.grace.repositories.RoleRepository;
import com.example.grace.repositories.UserRepository;
import com.example.grace.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getPseudo(), loginRequest.getPassword()));

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Vérifier si l'utilisateur est actif
            User user = userRepository.findByPseudo(userDetails.getUsername())
                    .orElseThrow(() -> new CustomException("USER_NOT_FOUND", "Error: User not found."));

            if (!user.isActive()) {
                throw new CustomException("USER_DISABLED", "Error: This account is deactivated.");
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles, userDetails.isFirstLogin());
        } catch (BadCredentialsException e) {
            throw new CustomException("INVALID_CREDENTIALS", "Error: Invalid username or password.");
        }
    }

    @Override
    public MessageResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(changePasswordRequest.getUserId())
                .orElseThrow(() -> new CustomException("USER_NOT_FOUND", "Error: User not found."));

        // Check if the old password matches
        if (!encoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new CustomException("INVALID_OLD_PASSWORD", "Error: Old password is incorrect.");
        }

        // Update password
        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        user.setFirstLogin(false); // Mark as not first login anymore
        userRepository.save(user);

        return new MessageResponse("Password changed successfully!");
    }

    @Override
    public MessageResponse registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByPseudo(signUpRequest.getPseudo())) {
//            throw new CustomException("Error: Username is already taken!");
            throw new  CustomException("USER_ALREADY_EXISTS", "Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            throw new CustomException("Error: Email is already in use!");
            throw new CustomException("EMAIL_ALREADY_IN_USE", "Error: Email is already in use!");
        }


        // Vérification de la force du mot de passe
        if (!isPasswordStrong(signUpRequest.getPassword())) {
            throw new CustomException("PASSWORD_NOT_STRONG", "Error: Password not strong!");
        }


        // Check if password and confirmPassword match
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new CustomException("PASSWORDS_DO_NOT_MATCH", "Error: Password and confirm password do not match!");
        }


        User user = new User(
                signUpRequest.getPseudo(),
                signUpRequest.getNom(),
                signUpRequest.getEmail(),
                signUpRequest.getTelephone(),
                signUpRequest.getVille(),
                encoder.encode(signUpRequest.getPassword())  // <-- encoder ici !
        );



        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new CustomException("ROLE_NOT_FOUND", "Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new CustomException("ROLE_NOT_FOUND", "Error: Role is not found."));
                        roles.add(adminRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new CustomException("ROLE_NOT_FOUND", "Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    // Méthode pour vérifier la force du mot de passe
    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 && // Longueur minimale
                password.matches(".*[A-Z].*") && // Au moins une majuscule
                password.matches(".*[a-z].*") && // Au moins une minuscule
                password.matches(".*\\d.*") && // Au moins un chiffre
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"); // Au moins un caractère spécial
    }

    // Méthode pour obtenir un utilisateur par son ID
    @Override
    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new CustomException("USER_NOT_FOUND", "Error: User not found.");
        }
        return userOptional.get(); // Renvoie l'utilisateur trouvé
    }

    @Override
    public MessageResponse updateUser(Long userId, SignupRequest updateRequest) {

        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new CustomException("USER_NOT_FOUND", "Error: User not found.");
        }

        User user = userOptional.get();

        // Vérifier si un mot de passe est fourni pour la mise à jour
        if (updateRequest.getPassword() != null) {
            // Vérifier si le mot de passe et la confirmation du mot de passe correspondent
            if (!updateRequest.getPassword().equals(updateRequest.getConfirmPassword())) {
                throw new CustomException("PASSWORDS_DO_NOT_MATCH", "Error: Password and confirm password do not match!");
            }

            // Vérifier si le mot de passe est suffisamment fort
            if (!isPasswordStrong(updateRequest.getPassword())) {
                throw new CustomException("PASSWORD_NOT_STRONG", "Error: Password not strong!");
            }

            // Encoder le mot de passe et mettre à jour le champ
            user.setPassword(encoder.encode(updateRequest.getPassword()));

            // Mettre à jour le champ `firstLogin` à 1, car l'utilisateur change son mot de passe
            user.setFirstLogin(true);  // 1 indique qu'il a changé son mot de passe pour la première fois
        }

        // Sauvegarder l'utilisateur dans la base de données
        userRepository.save(user);

        // Retourner une réponse
        return new MessageResponse("Password updated successfully!");
    }




    @Override
    // Méthode pour lister tous les utilisateurs
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> listUserPages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    @Override
    // Méthode pour lister tous les utilisateurs
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @Override
    public MessageResponse updateProfil(Long id, SignupRequest updateRequest) {
        // Recherche de l'utilisateur existant dans la base de données
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID " + id));

        // Log pour vérifier l'utilisateur récupéré
        System.out.println("Utilisateur trouvé : " + existingUser.getPseudo());

        // Mise à jour du nom d'utilisateur et de l'email
        existingUser.setPseudo(updateRequest.getPseudo());
        existingUser.setNom(updateRequest.getNom());
        existingUser.setEmail(updateRequest.getEmail());
        existingUser.setTelephone(updateRequest.getTelephone());
        existingUser.setVille(updateRequest.getVille());

        // Récupération des rôles sous forme de String
        Set<String> strRoles = updateRequest.getRole();
        Set<Role> roles = new HashSet<>();  // Créer un Set de roles

        if (strRoles != null && !strRoles.isEmpty()) {
            // Log pour voir les rôles reçus
            System.out.println("Rôles reçus : " + strRoles);

            // Parcours des rôles envoyés pour les convertir en objets Role
            strRoles.forEach(role -> {
                Role userRole = roleRepository.findByName(ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Rôle non trouvé : " + role));  // Recherche du rôle dans la base de données
                roles.add(userRole);  // Ajout du rôle trouvé au Set
            });
        } else {
            // Si aucun rôle n'est passé, attribuer un rôle par défaut
            System.out.println("Aucun rôle spécifié, attribuer un rôle par défaut.");
            Role defaultRole = roleRepository.findByName(ERole.valueOf("ROLE_USER_MIS"))
                    .orElseThrow(() -> new RuntimeException("Rôle par défaut non trouvé"));
            roles.add(defaultRole);  // Ajout du rôle par défaut
        }

        // Mise à jour des rôles de l'utilisateur
        existingUser.setRoles(roles);

        // Enregistrement de l'utilisateur mis à jour dans la base de données
        userRepository.save(existingUser);

        // Retourner une réponse de succès
        return new MessageResponse("Profil mis à jour avec succès !");
    }

    @Override
    // Méthode pour supprimer un utilisateur
    public MessageResponse deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new CustomException("USER_NOT_FOUND", "Error: User not found.");
        }

        userRepository.delete(userOptional.get());
        return new MessageResponse("User deleted successfully!");
    }

    @Override
    public MessageResponse activateOrDeactivateUser(Long userId, boolean activate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("USER_NOT_FOUND", "Error: User not found."));

        user.setActive(activate);
        userRepository.save(user);

        String message = activate ? "User activated successfully!" : "User deactivated successfully!";
        return new MessageResponse(message);
    }

}

