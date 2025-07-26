package com.spring.carrentalapp.Car_Rental.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.spring.carrentalapp.Car_Rental.entity.RentACar;

@Repository
public interface RentACarRepository extends JpaRepository<RentACar, Long>  {

	List<RentACar> findAllByUserId(Long userId);
	
	@Query("SELECT b FROM RentACar b WHERE b.car.id = :carId AND b.status = 'CONFIRMED' AND " +
	           "b.fromDate <= :toDate AND b.toDate >= :fromDate")
	List<RentACar> findOverlappingBookings(Long carId, LocalDateTime fromDate, LocalDateTime toDate);
}
