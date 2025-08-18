package com.spring.carrentalapp.Car_Rental.services.auth;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.spring.carrentalapp.Car_Rental.dto.LoginRequest;
import com.spring.carrentalapp.Car_Rental.dto.SignupRequest;
import com.spring.carrentalapp.Car_Rental.dto.UserDto;
import com.spring.carrentalapp.Car_Rental.entity.User;
import com.spring.carrentalapp.Car_Rental.repository.UserRepository;

@Service
public class AuthServiceImp implements AuthService {

	private final UserRepository userRepo;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;
	
	public AuthServiceImp(UserRepository userRepo, 
						  KafkaTemplate<String, String> kafkaTemplate,
						  ObjectMapper objectMapper) {
		this.userRepo = userRepo;
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	@Override
	public UserDto createUser(SignupRequest signupRequest) {
		User user = new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(signupRequest.getPassword());
		User createdUser = userRepo.save(user);
		UserDto createdUserDto = new UserDto();
		createdUserDto.setId(createdUser.getId());
		createdUserDto.setName(createdUser.getName());
		createdUserDto.setEmail(createdUser.getEmail());
		
		try {
            // Create event JSON
            String event = objectMapper.writeValueAsString(createdUserDto);
            
            // Publish to Kafka
            kafkaTemplate.send("user-registrations", event);
            System.out.println("📤 Published UserRegistered event: " + event);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return createdUserDto;
	}

	@Override
	public Optional<User> loginUser(LoginRequest loginRequest) {
		Optional<User> user = userRepo.findFirstByEmail(loginRequest.getEmail());
		return user;
	}
}
