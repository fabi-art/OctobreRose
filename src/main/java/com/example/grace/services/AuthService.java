//package com.example.grace.services;
//
//public interface AuthService {
//}
package com.example.grace.services;

import com.example.grace.entities.Role;
import com.example.grace.entities.User;
import com.example.grace.payload.request.ChangePasswordRequest;
import com.example.grace.payload.request.LoginRequest;
import com.example.grace.payload.request.SignupRequest;
import com.example.grace.payload.response.JwtResponse;
import com.example.grace.payload.response.MessageResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    MessageResponse registerUser(SignupRequest signUpRequest);
    public MessageResponse deleteUser(Long userId);

    public MessageResponse updateUser(Long userId, SignupRequest updateRequest);

    public MessageResponse updateProfil(Long userId, SignupRequest updateRequest);

    public List<User> listUsers();

    public User getUserById(Long userId);

    public MessageResponse changePassword(ChangePasswordRequest changePasswordRequest);

    public MessageResponse activateOrDeactivateUser(Long userId, boolean activate);

    public List<Role> listRoles();

    public Page<User> listUserPages(int page, int size);
}