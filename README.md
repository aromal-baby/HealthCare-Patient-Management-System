# HealthCare-Patient-Management-System
A comprehensive healthcare database system that leverages both SQL (MySQL) and NoSQL (MongoDB) technologies to efficiently manage patient data, appointments, and medical records. This hybrid approach demonstrates the optimal use of relational and document-oriented databases for different types of healthcare information.

Key Features

Patient Management: Store structured patient demographics in MySQL with secure access controls
Appointment Scheduling: Track and manage appointments with automatic validation and conflict resolution
Medical Records: Store semi-structured medical data in MongoDB for maximum flexibility
Staff Management: Maintain medical staff information with specialties and availability
Medical History: Flexible document storage for allergies, chronic conditions, and family history
Test Results: Store and query variable lab results with their interpretations
Cross-Database Queries: Seamless data retrieval across both database systems

Technology Stack

Backend: Java with Spring Boot framework
SQL Database: MySQL for patient demographics, appointments, and staff information
NoSQL Database: MongoDB for medical records, test results, and patient history
Frontend: Thymeleaf templates with Bootstrap for responsive UI
Build Tool: Maven for dependency management and build automation

Database Architecture
This project demonstrates the effective use of a hybrid database architecture:
MySQL Schema

patients: Structured demographic information with proper normalization
medical_staff: Healthcare provider details with credentials and specialties
appointments: Scheduling information with ACID transaction compliance

MongoDB Collections

medical_records: Flexible schema for various medical observations
test_results: Adaptable structure for different types of medical tests
patient_history: Document-based storage for evolving patient history

Implementation Highlights

Entity relationship modeling with proper normalization for SQL data
Document design with embedding and referencing strategies for MongoDB
Cross-database integration with transaction management
Service-oriented architecture with clear separation of concerns
Repository pattern implementation for data access

Academic Project
This project was developed as part of the M605 Advanced Databases course at Gisma University of Applied Sciences. It demonstrates database design principles, hybrid database architecture, and software engineering best practices.
Video Demonstration
A comprehensive video demonstration is available here showing the system's functionality, database querying, and integration between MySQL and MongoDB.
Installation and Setup
Detailed instructions for setting up both database systems and running the application can be found in the Installation Guide.
License
This project is intended for academic purposes only. All rights reserved.



## Technologies Used

- Java 11
- Spring Boot 2.7.x
- MySQL 8.0
- MongoDB 5.0
- Thymeleaf
- Bootstrap 5

## Features

- Patient registration and management
- Medical staff management
- Appointment scheduling
- Medical records storage and retrieval
- Test results tracking
- Patient history with allergies and chronic conditions

## Database Design

### MySQL Database Schema
- Patients (structured demographic data)
- Medical Staff (healthcare provider information)
- Appointments (scheduled visits)

### MongoDB Collections
- Medical Records (flexible medical notes)
- Test Results (variable lab results)
- Patient History (medical history with varying structure)

## Setup Instructions

1. Clone the repository
2. Configure MySQL and MongoDB connections in application.properties
3. Run the application using Maven: `mvn spring-boot:run`
4. Access the application at http://localhost:8080

## Development Process

This project demonstrates the integration of SQL and NoSQL databases in a healthcare context, showing how structured and unstructured data can be effectively managed in a single application.

## Video Demonstration

[Link to video demonstration]
