Stock Dashboard API 

1) Introduction

   The Stocks Dashboard application exposes rest services to store Stock details. Below mentioned are different REST API it exposes to perform CRUD operations:

   i) Add Stocks: This POST REST API expects Stock Name and Stock price as input and stores the information with the latest timestamp into the database.

   ii)Update Stock: This PUT REST API expects the Stock Id and Stock Price as input and updates the stock if it exists in the database.

   iii)Search Stock by Id: This GET REST API expects Stock Id as input and provides Stock details like Stock Name, Price and latest updated timestamp.

   iv)Get all available Stocks : This GET REST API returns all the stocks present in the Database

   v) Remove Stock Information: This DELETE REST API expects a Stock Id as input and deletes the corresponding stock from the Database.
 
   vi)Authenticate user : This POST REST API expects username and password and return the JWT Token as response.


2) Technology Stack
   
   i)   Java 11
   
   ii)  Spring Boot : 2.4.3
   
   iii) Maven : 3.6.3
   
   iv)  H2 Database : 1.4.200
   
   v)   JWT : 0.9.1
   
   vi)  Lombok : 1.18.18
   
   vii) Junits : 4.2


3) Security 
   
    i) This application uses JWT Token based authentication and authorization. A USERS table is created and details like username, password(encrypted format), active status and role of the user is stored into it.
    
   ii) In order to consume the Stocks REST services, user needs to provide the correct user and password. 
   
   iii) The application checks if the username and password matches with the one stored into the database and also check if the user is active or not. If everything matches, it uses the username and role to create the JWT token and returns it as output to the user.

   iv) User needs to use the same JWT Token in the header of all the REST API requests to get the expected responses.

   v) Validity of the JWT Token is set to 1 hour for the Demo purpose but ideally it should be kept to 30 min in order to prevent security attacks.

   vi) After 1 hour, user would need to re-authenticate to receive a fresh JWT Token and continue consuming the REST API


4) Exception Handling
   
   i) This application use GlobalRestExceptionHandler approach with the help of Spring provided @RestControllerAdvice annotation.

   ii) All the exceptions are caught in this common class, and a user-defined message is sent as response.

5) Logging
   
   i) For logging, application uses Lombok provided Slf4j annotation and all the important methods are covered with logging.

6) Assumptions
  
   i) I have assumed that the user would not be providing the Stock id while requesting to add a stock, so I have auto generated stockId while storing stock information into the database.

   ii) I have added JWT Token based security to the application assuming that security is a must requirement while exposing rest services.

   iii) I have used InMemory database H2 to load the initial Stocks information and also the new stocks that would be added/updated while consuming the REST services.


7) Steps to configure and run the application
   
   i) Pre-requisites : You should have Java 8, Git Bash and above version and Maven 3.X installed in your system to run the application.

   ii) You can use git command to clone the application from the below mentioned link or download the ZIP.
      https://github.com/ParasGosalia/stock-dashboard-api.git
      
   iii) Please follow below mentioned commands:
   
       a) Got to a folder where you want to clone the app and Open git bash there. 
          Now run the following commands
          git clone https://github.com/ParasGosalia/stock-dashboard-api.git
          cd stock-dashboard-api
          git checkout master
          git checkout feature/version-1.0.0

       b) Now you can either build the application using command prompt or 
          you can open this application in Intellij/Eclipse 
          and run the main class as SpringBoot application.
                                   