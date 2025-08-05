package com.spring.carrentalapp.Car_Rental.services.s3;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

	void uploadFile(MultipartFile file) throws IOException;

	byte[] downloadFile(String key);

	String generatePresignedUrl(String key);

}
