package com.spring.carrentalapp.Car_Rental.services.s3;

import java.io.IOException;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
public class S3ServiceImplementation implements S3Service{

	@Value("${cloud.aws.bucket.name}")
	private String bucketName;
	
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    
    public S3ServiceImplementation(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }
    
	@Override
	public void uploadFile(MultipartFile file) throws IOException {
		s3Client.putObject(PutObjectRequest.builder()
				.bucket(bucketName)
				.key(file.getOriginalFilename())
				.build(), RequestBody.fromBytes(file.getBytes()));
	}
	
	@Override
	public byte[] downloadFile(String key) {
		ResponseBytes<GetObjectResponse> objectAsBytes = s3Client.getObjectAsBytes(GetObjectRequest
				.builder()
				.bucket(bucketName)
				.key(key)
				.build());
		return objectAsBytes.asByteArray();
	}
	
	@Override
    public String generatePresignedUrl(String key) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .getObjectRequest(objectRequest)
                .build();

        PresignedGetObjectRequest presigned = s3Presigner.presignGetObject(presignRequest);
        return presigned.url().toString();
    }
	
}
