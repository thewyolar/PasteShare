package ru.pasteshare.serviceapi.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.pasteshare.serviceapi.service.StorageService;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

@Component
public class StorageServiceImpl implements StorageService {

    private final S3Client s3Client;

    @Value("${aws.bucket_name}")
    private String BUCKET_NAME;

    public StorageServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void savePaste(String filename, String content) throws IOException {
        byte[] bytes = content.getBytes();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(filename)
                .build();

        PutObjectResponse response = s3Client.putObject(putObjectRequest,
                RequestBody.fromBytes(bytes));

        System.out.println("Paste saved to S3. ETag: " + response.eTag());
    }
}