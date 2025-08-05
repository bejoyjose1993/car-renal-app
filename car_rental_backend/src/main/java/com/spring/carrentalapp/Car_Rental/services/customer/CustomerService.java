package com.spring.carrentalapp.Car_Rental.services.customer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.carrentalapp.Car_Rental.dto.CarDto;
import com.spring.carrentalapp.Car_Rental.dto.RentACarDto;
import com.spring.carrentalapp.Car_Rental.entity.Car;

public interface CustomerService {
	List<CarDto> getAllCArs();
	
	boolean bookCar(RentACarDto rentACarDto);
	
	CarDto getCarById(Long carId);
	
	List<RentACarDto> getBookingsByUserId(Long userId);
	
	List<CarDto> searchAvailableCars(String type, LocalDateTime from, LocalDateTime to);
	
}
