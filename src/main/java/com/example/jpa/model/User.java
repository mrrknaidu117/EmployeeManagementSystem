package com.example.jpa.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false,unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
}
