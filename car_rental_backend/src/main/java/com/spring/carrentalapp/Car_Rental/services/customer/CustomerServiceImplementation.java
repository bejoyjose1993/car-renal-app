package com.spring.carrentalapp.Car_Rental.services.customer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.carrentalapp.Car_Rental.dto.CarDto;
import com.spring.carrentalapp.Car_Rental.dto.RentACarDto;
import com.spring.carrentalapp.Car_Rental.entity.Car;
import com.spring.carrentalapp.Car_Rental.entity.RentACar;
import com.spring.carrentalapp.Car_Rental.entity.User;
import com.spring.carrentalapp.Car_Rental.repository.CarRepository;
import com.spring.carrentalapp.Car_Rental.repository.RentACarRepository;
import com.spring.carrentalapp.Car_Rental.repository.UserRepository;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class CustomerServiceImplementation implements CustomerService {
	
	@Value("${cloud.aws.bucket.name}")
	private String bucketName;
	
	private final CarRepository carRepository;
	private final UserRepository userRepository;
	private final RentACarRepository rentACarRepository;
	private S3Client s3Client;
	
	CustomerServiceImplementation(CarRepository carRepository,
								  UserRepository userRepository,
								  RentACarRepository rentACarRepository,
								  S3Client s3Client){
		this.carRepository = carRepository;
		this.userRepository = userRepository;
		this.rentACarRepository = rentACarRepository;
		this.s3Client = s3Client;
	}
	
	@Override
	public List<CarDto> getAllCArs() {
		return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
	}

	@Override
	public boolean bookCar(RentACarDto rentACarDto) {
		Optional<Car> optionalCar = carRepository.findById(rentACarDto.getCarId()); 
		Optional<User> optionalUser = userRepository.findById(rentACarDto.getUserId());
		if(optionalCar.isPresent() && optionalUser.isPresent()) {
			Car currentCar = optionalCar.get();
			RentACar rentACar = new RentACar();
			rentACar.setUser(optionalUser.get());
			rentACar.setCar(currentCar);
			rentACar.setFromDate(rentACarDto.getFromDate());
			rentACar.setToDate(rentACarDto.getToDate());
			rentACar.setBookCarStatus("CONFIRMED");
			long days = ChronoUnit.DAYS.between(rentACarDto.getFromDate(), rentACarDto.getToDate());
			rentACar.setDays(Math.abs(days));
			rentACar.setPrice(currentCar.getPrice());
			rentACarRepository.save(rentACar);
			return true;
		}
		return false;
	}

	@Override
	public CarDto getCarById(Long carId) {
		Optional<Car> optionalCar = carRepository.findById(carId);
		return optionalCar.map(Car::getCarDto).orElse(null);
	}

	@Override
	public List<RentACarDto> getBookingsByUserId(Long userId) {
		return rentACarRepository.findAllByUserId(userId).stream().map(RentACar::getRentACarDto).collect(Collectors.toList());

	}

	@Override
	public List<Car> searchAvailableCars(String type, LocalDateTime from, LocalDateTime to) {
		List<Car> cars = carRepository.findAllByType(type);
		return cars.stream().filter(car -> rentACarRepository.findOverlappingBookings(car.getId(), from, to).isEmpty())
	            .collect(Collectors.toList());
	}
	
	public void uploadFile(MultipartFile file) throws IOException {
		s3Client.putObject(PutObjectRequest.builder()
				.bucket(bucketName)
				.key(file.getOriginalFilename())
				.build(), RequestBody.fromBytes(file.getBytes()));
	}
	
	public byte[] downloadFile(String key) {
		ResponseBytes<GetObjectResponse> objectAsBytes = s3Client.getObjectAsBytes(GetObjectRequest
				.builder()
				.bucket(bucketName)
				.key(key)
				.build());
		return objectAsBytes.asByteArray();
	}
}
