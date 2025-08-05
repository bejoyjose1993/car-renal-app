package com.spring.carrentalapp.Car_Rental.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.carrentalapp.Car_Rental.dto.CarDto;
import com.spring.carrentalapp.Car_Rental.dto.RentACarDto;
import com.spring.carrentalapp.Car_Rental.entity.Car;
import com.spring.carrentalapp.Car_Rental.services.customer.CustomerService;
import com.spring.carrentalapp.Car_Rental.services.s3.S3Service;



@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	private final CustomerService customerService;
	private final S3Service s3Service;
	public CustomerController(CustomerService customerService, S3Service s3Service){
		this.customerService = customerService;
		this.s3Service = s3Service;
	}
	
	@GetMapping("/cars")
	public ResponseEntity<?> getAllCars(){
		return ResponseEntity.ok(customerService.getAllCArs());
	}
	
	@PostMapping("/car/book")
	public ResponseEntity<Void> bookACar(@RequestBody RentACarDto rentACarDto){
		boolean success =customerService.bookCar(rentACarDto);
		if(success) return ResponseEntity.status(HttpStatus.CREATED).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("/cars/{carId}")
	public ResponseEntity<CarDto> getCarById(@PathVariable Long carId){
		CarDto carDto = customerService.getCarById(carId);
		if(carDto== null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(carDto);
				
	}
	
	@GetMapping("/cars/bookings/{userID}")
	public ResponseEntity<List<RentACarDto>> getBookingsByUserId(@PathVariable Long userID){
		return ResponseEntity.ok(customerService.getBookingsByUserId(userID));
				
	}
	
    @GetMapping("/search")
    public ResponseEntity<List<CarDto>> search(
      @RequestParam String type, 
      @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
      @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {
        return ResponseEntity.ok(customerService.searchAvailableCars(type, fromDate, toDate));
    }
    
	@PostMapping("/car/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException{
		s3Service.uploadFile(file);
		return ResponseEntity.ok("File Uploaded Successfully");
	}
	
	@GetMapping("/car/download/{fileName}")
	public ResponseEntity<byte[]> download(@PathVariable String fileName){
		byte[] data = s3Service.downloadFile(fileName);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment: Filename=")
				.body(data);
				
	}
	
}
