package com.spring.carrentalapp.Car_Rental.entity;

import java.time.LocalDateTime;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.carrentalapp.Car_Rental.dto.RentACarDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RentACar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime fromDate;
	private LocalDateTime toDate;
	private Long days;
	private Long price;
	private String status;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "car_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Car car;

	public RentACar() {}

	public RentACar(Long id, LocalDateTime fromDate, LocalDateTime toDate, Long days, Long price, User user, Car car, String status) {
		super();
		this.id = id;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.days = days;
		this.price = price;
		this.user = user;
		this.car = car;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDateTime fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDateTime getToDate() {
		return toDate;
	}

	public void setToDate(LocalDateTime toDate) {
		this.toDate = toDate;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	
	public String getBookCarStatus() {
		return status;
	}

	public void setBookCarStatus(String status) {
		this.status = status;
	}
	
	public RentACarDto getRentACarDto() {
		RentACarDto rentACarDto = new RentACarDto();
		rentACarDto.setId(id);
		rentACarDto.setDays(days);
		rentACarDto.setPrice(price);
		rentACarDto.setToDate(toDate);
		rentACarDto.setFromDate(fromDate);
		rentACarDto.setEmail(user.getEmail());
		rentACarDto.setUserName(user.getName());
		rentACarDto.setUserId(user.getId());
		rentACarDto.setCarId(car.getId());
		rentACarDto.setStatus(status);
		return rentACarDto;
	}


}
