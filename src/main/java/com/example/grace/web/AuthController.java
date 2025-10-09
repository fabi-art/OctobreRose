package com.example.grace.web;

import com.example.grace.entities.User;
import com.example.grace.exceptions.CustomException;
import com.example.grace.exceptions.ErrorResponse;
import com.example.grace.payload.request.ChangePasswordRequest;
import com.example.grace.payload.request.LoginRequest;
import com.example.grace.payload.request.SignupRequest;
import com.example.grace.payload.response.JwtResponse;
import com.example.grace.payload.response.MessageResponse;
import com.example.grace.services.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		MessageResponse response = authService.registerUser(signUpRequest);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
		return ResponseEntity.ok(jwtResponse);
	}
	@PostMapping("/changePassword")
	public ResponseEntity<MessageResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
		MessageResponse response = authService.changePassword(changePasswordRequest);
		return ResponseEntity.ok(response);
	}

//	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")

	/*@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		MessageResponse messageResponse = authService.registerUser(signUpRequest);
		if (messageResponse.getMessage().startsWith("Error")) {
			return ResponseEntity.badRequest().body(messageResponse);
		}
		return ResponseEntity.ok(messageResponse);
	}*/

	/*// Endpoint pour lister tous les utilisateurs
	@GetMapping("/users")
	public ResponseEntity<List<User>> listUsers() {
		List<User> users = authService.listUsers();
		return ResponseEntity.ok(users);
	}


>>>>>>> origin/dev-anita
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user = authService.getUserById(id);
		return ResponseEntity.ok(user); // Retourne l'utilisateur trouvé
	}


//	@PreAuthorize("hasRole('ROLE_ADMIN')")

	@PutMapping("/users/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody SignupRequest updateRequest) {
		MessageResponse messageResponse = authService.updateUser(id, updateRequest);
		if (messageResponse.getMessage().startsWith("Error")) {
			return ResponseEntity.badRequest().body(messageResponse);
		}
		return ResponseEntity.ok(messageResponse);
	}

<<<<<<< HEAD

//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/users/update/p/{id}")
	public ResponseEntity<?> profilUser(@PathVariable Long id, @Valid @RequestBody SignupRequest updateRequest) {
		MessageResponse messageResponse = authService.updateProfil(id, updateRequest);
=======
	// Endpoint pour supprimer un utilisateur
	@DeleteMapping("/users/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		MessageResponse messageResponse = authService.deleteUser(id);
>>>>>>> origin/dev-anita
		if (messageResponse.getMessage().startsWith("Error")) {
			return ResponseEntity.badRequest().body(messageResponse);
		}
		return ResponseEntity.ok(messageResponse);
<<<<<<< HEAD
	}
=======
	}*/

}



