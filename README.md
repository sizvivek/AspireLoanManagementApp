# AspireLoanManagementApp

AspireLoanManagementApp is a Spring Boot(Java based) application that allows customers to apply for loans, admins to approve loan applications, and customers to make repayments.

  ## Table of Contents
  - [Prerequisites](#prerequisites)
  - [Getting Started](#getting-started)
    - [Installation](#installation)
    - [Configuration](#configuration)
  - [Usage](#usage)
  - [API Documentation](#api-documentation)
  
  ## Prerequisites
    To run this project, you will need the following prerequisites:
    
    - Java 8 or higher
    - MySQL (or your preferred database)
    - Maven (if not using an IDE(e.g. IntelliJ) with built-in support)
  
  ## Getting Started

    Follow these steps to set up and run the project locally.
  
    ### Installation
      1. Clone the repository:
      
           ```sh
           git clone https://github.com/yourusername/loan-management-system.git
         
      2. Navigate to the project directory
         cd loan-management-system
    
    ### Configuration
      Before running the project, you need to configure the database and build it.
      
      1. Database Configuration:
         
        Create a MySQL database named loan_management_db.
        Update the database connection properties in src/main/resources/application.properties:
          
                spring.datasource.url=jdbc:mysql://localhost:3306/loan_management_db
                spring.datasource.username=your_database_username
                spring.datasource.password=your_database_password
                
      2. Build the project:
            
            mvn clean install
  
  ## Usage:
    To use the Loan Management System:
      * Run the Spring Boot application.
      * Access the API endpoints to apply for loans, approve applications, and make repayments.


  ## API Documentation
      API documentation for the Loan Management System can be found at link.






