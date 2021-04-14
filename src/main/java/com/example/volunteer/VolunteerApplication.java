package com.example.volunteer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@EnableScheduling
@SpringBootApplication
public class VolunteerApplication {

	@Bean
	public MultipartConfigElement multipartConfigElement(@Value("${spring.servlet.multipart.max-file-size}")String maxFileSize, @Value("${spring.servlet.multipart.max-request-size}") String maxRequestSize) {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.parse(maxFileSize));
		factory.setMaxRequestSize(DataSize.parse(maxRequestSize));
		return factory.createMultipartConfig();
	}

	public static void main(String[] args) {
		SpringApplication.run(VolunteerApplication.class, args);
	}

}