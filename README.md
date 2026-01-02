# Wallet Transaction Control System

## Overview
The Wallet Transaction Control System is a backend application designed to manage digital wallet operations such as wallet creation, credit, debit, and balance tracking.It simulates core wallet transaction logic used in fintech and payment systems, replacing manual balance tracking with a structured and controlled digital solution that ensures accuracy, validation, and consistency.This repository contains the backend components required to manage wallet transactions securely and efficiently.

## Key Objectives
- Digitally manage wallet balances
- Control credit and debit transactions with validations
- Maintain transaction integrity
- Provide a scalable backend design for future API or UI integration
- Demonstrate clean backend architecture and best practices

## Features
- Wallet creation
- Credit amount to wallet
- Debit amount from wallet
- Balance validation and control
- Centralized business logic
- Custom exception handling
- Modular and extendable design

## Technology Stack
- **Backend:** Java
- **Architecture:** Servlet-based layered architecture
- **Database:** MySQL
- **API Style:** REST-like endpoints
- **Build Tool:** Maven
- **Server:** Apache Tomcat

##Prerequisites
- Java (JDK 8 or above)
- MySQL database
- Apache Tomcat
- Git
- IDE (IntelliJ IDEA / Eclipse)

Ensure database credentials are properly configured before running the application.

## Application Usage
- Create a wallet for a user
- Credit money into the wallet
- Debit money from the wallet (with balance validation)
- Retrieve wallet balance information
- Handle invalid operations using business exceptions

## Sample API Endpoints

| Method | Endpoint | Description |
|------|---------|-------------|
| POST | /app | Create wallet |
| GET | /app | Check service status / wallet info |


## Validations Implemented
- Prevents debit when balance is insufficient
- Prevents negative or invalid transaction amounts
- Centralized business exception handling
- Safe database connection handling

## Future Enhancements
- REST APIs using Spring Boot
- Authentication and authorization
- Transaction history table
- Admin dashboard
- Concurrency handling
- Unit testing and SonarQube integration

## Author
Govindavasan R
GitHub: https://github.com/vijaybala16862
