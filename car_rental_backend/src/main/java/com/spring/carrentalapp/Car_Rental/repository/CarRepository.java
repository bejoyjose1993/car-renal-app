package com.spring.carrentalapp.Car_Rental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.spring.carrentalapp.Car_Rental.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	List<Car> findAllByType(String type);
}
