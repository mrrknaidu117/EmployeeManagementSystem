package com.example.jpa.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.jpa.dto.auth.LoginDto;
import com.example.jpa.dto.auth.SignUpDto;
import com.example.jpa.service.AuthService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
		String token = authService.login(loginDto);
		return ResponseEntity.ok(token);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto){
		authService.register(signUpDto);
		return ResponseEntity.ok("User register successfully");
	}
}