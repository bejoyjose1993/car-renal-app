package com.car.rental.mailing.car_rental_mailing.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotificationConsumerImplementation implements NotificationConsumer {

    private final JavaMailSender mailSender;
    private final ObjectMapper objectMapper;

    public NotificationConsumerImplementation(JavaMailSender mailSender, ObjectMapper objectMapper) {
        this.mailSender = mailSender;
        this.objectMapper = objectMapper;
    }
	
	@Override
	@KafkaListener(topics = "user-registrations", groupId = "notification-group")
	public void consume(String message) {
		 try {
	            JsonNode json = objectMapper.readTree(message);
	            String email = json.get("email").asText();
	            String name = json.get("name").asText();

	            // send welcome email
	            SimpleMailMessage mailMessage = new SimpleMailMessage();
	            mailMessage.setTo(email);
	            mailMessage.setSubject("Welcome to Car Rental 🚗");
	            mailMessage.setText("Hi " + name + ",\n\nThanks for registering with Car Rental Service!");

	            mailSender.send(mailMessage);

	            System.out.println("📧 Email sent to: " + email);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}
