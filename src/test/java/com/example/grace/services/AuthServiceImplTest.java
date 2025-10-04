//package com.example.grace.services;
//
//
//import com.example.grace.entities.ERole;
//import com.example.grace.entities.Role;
//import com.example.grace.entities.User;
//import com.example.grace.exceptions.CustomException;
//import com.example.grace.payload.request.LoginRequest;
//import com.example.grace.payload.request.SignupRequest;
//import com.example.grace.payload.response.JwtResponse;
//import com.example.grace.payload.response.MessageResponse;
//import com.example.grace.repositories.RoleRepository;
//import com.example.grace.repositories.UserRepository;
//import com.example.grace.security.jwt.JwtUtils;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//class AuthServiceImplTest {
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private RoleRepository roleRepository;
//
//    @Mock
//    private PasswordEncoder encoder;
//
//    @Mock
//    private JwtUtils jwtUtils;
//
//    @InjectMocks
//    private AuthServiceImpl authService;
//
//    private LoginRequest loginRequest;
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        loginRequest = new LoginRequest();
//        loginRequest.setUsername("testUser");
//        loginRequest.setPassword("testPassword");
//
//        user = new User(1L, "testUser", "testEmail@example.com", "encodedPassword", true, Collections.emptyList());
//    }
//
//    @Test
//    void testAuthenticateUser_ValidCredentials() {
//        // Mocking Authentication
//        Authentication authentication = mock(Authentication.class);
//        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "testUser", "testEmail@example.com", "encodedPassword", new ArrayList<>(), false);
//        when(authenticationManager.authenticate(any())).thenReturn(authentication);
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
//        when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwtToken");
//
//        // Test the method
//        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
//
//        // Verifying the results
//        assertNotNull(jwtResponse);
//        assertEquals("jwtToken", jwtResponse.getAccessToken());
//        assertEquals("testUser", jwtResponse.getUsername());
//    }
//
//    @Test
//    public void testAuthenticateUser_InvalidCredentials() {
//        // Arrange
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("testuser");
//        loginRequest.setPassword("wrongpassword");
//
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenThrow(new BadCredentialsException("Invalid credentials"));
//
//        // Act & Assert
//        CustomException exception = assertThrows(CustomException.class,
//                () -> authService.authenticateUser(loginRequest));
//        assertEquals("INVALID_CREDENTIALS", exception.getCode());
//        assertEquals("Error: Invalid username or password.", exception.getMessage());
//    }
//
//
//    @Test
//    public void testRegisterUser_Success() {
//        SignupRequest signUpRequest = new SignupRequest();
//        signUpRequest.setUsername("newuser");
//        signUpRequest.setEmail("newuser@example.com");
//        signUpRequest.setPassword("Password1!");
//        signUpRequest.setConfirmPassword("Password1!");
//        signUpRequest.setRole(Set.of("user"));
//
//        when(userRepository.existsByUsername(anyString())).thenReturn(false);
//        when(userRepository.existsByEmail(anyString())).thenReturn(false);
//        when(roleRepository.findByName(any(ERole.class))).thenReturn(Optional.of(new Role(ERole.ROLE_USER_MIS)));
//        when(encoder.encode(anyString())).thenReturn("encodedPassword");
//
//        MessageResponse response = authService.registerUser(signUpRequest);
//
//        assertEquals("User registered successfully!", response.getMessage());
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    public void testRegisterUser_UsernameAlreadyExists() {
//        SignupRequest signUpRequest = new SignupRequest();
//        signUpRequest.setUsername("existinguser");
//        signUpRequest.setEmail("newuser@example.com");
//
//        when(userRepository.existsByUsername(anyString())).thenReturn(true);
//
//        CustomException exception = assertThrows(CustomException.class,
//                () -> authService.registerUser(signUpRequest));
//        assertEquals("USER_ALREADY_EXISTS", exception.getCode());
//        assertEquals("Error: Username is already taken!", exception.getMessage());
//    }
//
//    @Test
//    public void testRegisterUser_EmailAlreadyInUse() {
//        SignupRequest signUpRequest = new SignupRequest();
//        signUpRequest.setUsername("newuser");
//        signUpRequest.setEmail("existingemail@example.com");
//
//        when(userRepository.existsByEmail(anyString())).thenReturn(true);
//
//        CustomException exception = assertThrows(CustomException.class,
//                () -> authService.registerUser(signUpRequest));
//        assertEquals("EMAIL_ALREADY_IN_USE", exception.getCode());
//        assertEquals("Error: Email is already in use!", exception.getMessage());
//    }
//
//    @Test
//    public void testListUsers() {
//        List<User> users = List.of(new User("user1", "user1@example.com", "password"));
//        when(userRepository.findAll()).thenReturn(users);
//
//        List<User> result = authService.listUsers();
//
//        assertEquals(1, result.size());
//        assertEquals("user1", result.get(0).getUsername());
//    }
//
//    @Test
//    void testListUserPages() {
//        Pageable pageable = PageRequest.of(0, 5);
//        Page<User> usersPage = new PageImpl<>(List.of(new User(), new User()));
//        when(userRepository.findAll(pageable)).thenReturn(usersPage);
//
//        Page<User> result = authService.listUserPages(0, 5);
//
//        assertEquals(2, result.getContent().size());
//        verify(userRepository).findAll(pageable);
//    }
//
//    @Test
//    public void testGetUserById_Success() {
//        User user = new User("user1", "user1@example.com", "password");
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        User result = authService.getUserById(1L);
//
//        assertEquals("user1", result.getUsername());
//    }
//
//    @Test
//    public void testGetUserById_NotFound() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        CustomException exception = assertThrows(CustomException.class,
//                () -> authService.getUserById(1L));
//        assertEquals("USER_NOT_FOUND", exception.getCode());
//        assertEquals("Error: User not found.", exception.getMessage());
//    }
//
//
//    @Test
//    void testUpdateUser_Success() {
//        // Simulate the user being found
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(encoder.encode(anyString())).thenReturn("encodedPassword");
//
//        // Create a mock SignupRequest with new password
//        SignupRequest updateRequest = new SignupRequest();
//        updateRequest.setPassword("newPassword1!");
//        updateRequest.setConfirmPassword("newPassword1!");
//
//        // Call the method
//        MessageResponse response = authService.updateUser(1L, updateRequest);
//
//        // Verify that the user password is updated
//        assertNotNull(response);
//        assertEquals("Password updated successfully!", response.getMessage());
//        verify(userRepository, times(1)).save(user); // Ensure save is called
//    }
//
//    @Test
//    void testUpdateUser_PasswordsDoNotMatch() {
//        // Simulate the user being found
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        // Create a mock SignupRequest with mismatching passwords
//        SignupRequest updateRequest = new SignupRequest();
//        updateRequest.setPassword("newPassword");
//        updateRequest.setConfirmPassword("differentPassword");
//
//        // Test and expect exception
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            authService.updateUser(1L, updateRequest);
//        });
//
//        // Assert exception message
//        assertEquals("PASSWORDS_DO_NOT_MATCH", exception.getCode());
//        assertEquals("Error: Password and confirm password do not match!", exception.getMessage());
//    }
//
//
//
//
//    @Test
//    void testDeleteUser_UserNotFound() {
//        // Simulate the user not being found
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        // Test and expect exception
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            authService.deleteUser(1L);
//        });
//
//        // Assert exception message
//        assertEquals("USER_NOT_FOUND", exception.getCode());
//        assertEquals("Error: User not found.", exception.getMessage());
//    }
//
//    @Test
//    void testDeleteUser_Success() {
//        // Simulate the user being found
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        // Test the method
//        MessageResponse response = authService.deleteUser(1L);
//
//        // Assert success message
//        assertEquals("User deleted successfully!", response.getMessage());
//
//        // Verify that the delete method is called
//        verify(userRepository, times(1)).delete(user);
//    }
//
//    @Test
//    void testActivateOrDeactivateUser_UserNotFound() {
//        // Simulate the user not being found
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        // Test and expect exception
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            authService.activateOrDeactivateUser(1L, true);
//        });
//
//        // Assert exception message
//        assertEquals("USER_NOT_FOUND", exception.getCode());
//        assertEquals("Error: User not found.", exception.getMessage());
//    }
//
//    @Test
//    void testActivateOrDeactivateUser_Success() {
//        // Simulate the user being found
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        // Test the method
//        MessageResponse response = authService.activateOrDeactivateUser(1L, true);
//
//        // Assert success message
//        assertEquals("User activated successfully!", response.getMessage());
//
//        // Verify that the user is saved
//        verify(userRepository, times(1)).save(user);
//    }
//
//    @Test
//    void testListRoles() {
//        List<Role> roles = List.of(new Role(), new Role());
//        when(roleRepository.findAll()).thenReturn(roles);
//
//        List<Role> result = authService.listRoles();
//
//        assertEquals(2, result.size());
//        verify(roleRepository).findAll();
//    }
//
//
//}
