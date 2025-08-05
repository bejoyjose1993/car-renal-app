package com.spring.carrentalapp.Car_Rental.dto;

import org.springframework.web.multipart.MultipartFile;

public class CarDto {

	private Long id;
	private String brand;
	private String color;
	private String name;
	private String type;
	private Long price;
	private MultipartFile returnedImage;
	
	private String year;
	private String transmission;
	private String imageUrl;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Long getPrice() {
		return price;
	}
	
	public void setPrice(Long price) {
		this.price = price;
	}
	
	public MultipartFile getReturnedImage() {
		return returnedImage;
	}
	
	public void setReturnedImage(MultipartFile returnedImage) {
		this.returnedImage = returnedImage;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
