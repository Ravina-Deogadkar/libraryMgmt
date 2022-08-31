# LibraryMgmt
This project is developed as part of assignment which covers up four user story.
- User can view books in library
- User can borrow a book from the library
- User can borrow a copy of a book from the library
- User can return books to the library

## Pre-requisite
- Gradle 7.3
- Java 17.0.4
- Visual code/ Intellij (or any code editor)

## Installation
 - git clone 
 - cd to cloned repo location and open in editor
 - gradle clean build - build application and create jar file
 - gradle test - to run all test cases
 - gradle run - to run application
 
 ## Usage
 Once application is running, you can test application in the postman using
 
 ### Fetch books
 
 **GET: ** localhost:8080/books
 
 ![Fetch all books](https://github.com/Ravina-Deogadkar/libraryMgmt/blob/master/src/main/resources/static/get_request.png)
 
 ### Borrow books
 
 **PUT: ** localhost:8080/books/add/{bookid}
 ![Borrow books](https://github.com/Ravina-Deogadkar/libraryMgmt/blob/master/src/main/resources/static/borrow_request.png)
 
 ### Return books
 
 **PUT: ** localhost:8080/books/return/{bookid}
 ![Return books](https://github.com/Ravina-Deogadkar/libraryMgmt/blob/master/src/main/resources/static/return_request.png)
