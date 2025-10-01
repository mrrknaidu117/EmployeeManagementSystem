package com.example.jpa.service;

import com.example.jpa.dto.auth.LoginDto;
import com.example.jpa.dto.auth.SignUpDto;
import com.example.jpa.model.User;
import com.example.jpa.repository.UserRepository;
import com.example.jpa.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(loginDto.getUsername());
    }

    public void register(SignUpDto signUpDto) {
        // Check if username already exists
        if(userRepository.findByUsername(signUpDto.getUsername()).isPresent()){
            throw new RuntimeException("Error: Username is already taken!");
        }

        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword())); // Encrypt password

        userRepository.save(user);
    }
}