package com.healthcarepatientmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EntityScan("com.healthcarepatientmanagement.model.sql")
@EnableJpaRepositories("com.healthcarepatientmanagement.repository.sql")
@EnableMongoRepositories("com.healthcarepatientmanagement.repository.mongo")
public class HealthCarePatientManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthCarePatientManagementApplication.class, args);
    }
}