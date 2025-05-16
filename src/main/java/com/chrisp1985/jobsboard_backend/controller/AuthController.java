package com.chrisp1985.jobsboard_backend.controller;

import com.chrisp1985.jobsboard_backend.model.user.User;
import com.chrisp1985.jobsboard_backend.model.user.UserRequestDto;
import com.chrisp1985.jobsboard_backend.model.user.UserResponseDto;
import com.chrisp1985.jobsboard_backend.repository.UserRepository;
import com.chrisp1985.jobsboard_backend.security.JwtUtils;
import com.chrisp1985.jobsboard_backend.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private UserDetailsServiceImpl userDetailsService;
    @Autowired private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new UserResponseDto(user.getId(), user.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto userDto) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        String token = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }
}
