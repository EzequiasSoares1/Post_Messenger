# Post Messenger

Post Messenger is a project that offers a platform for creating, managing, and processing posts. It has been developed using Java and Spring Boot technologies, and it integrates with external services through WebClient.

## Features

- Creation of new posts
- Listing all posts
- Updating an existing post
- Disabling a post
- Reprocessing a post

## Used Technologies

- Java
- Spring Boot
- Hibernate
- H2 Database (in-memory)
- WebClient (for integration with external services)
- Springdoc OpenAPI UI

## API Documentation

The project includes an interactive API documentation, which can be accessed at the following URL: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html). This documentation provides details about available endpoints, their parameters, and responses, allowing you to test and better understand the functionalities provided by Post Messenger.

## Database Configuration

The project uses an in-memory H2 Database to store data. The database is automatically configured when the application is started.

## Available Endpoints

- `GET /posts`: Returns the list of all posts.
- `GET /posts/{id}`: Returns details of a specific post.
- `POST /posts`: Creates a new post.
- `PUT /posts/{id}`: Updates an existing post.
- `DELETE /posts/{id}`: Disables an existing post.
- `PUT /posts/{id}/reprocess`: Reprocesses an existing post.

## Contribution

Contributions are welcome! Feel free to create pull requests.

## How to Run

1. Clone the repository to your machine:
   
   "git clone https://github.com/kia735/Challenger_3-PostMessenger-"
   
2. Navigate to the project directory:

    cd Challenger_3-PostMessenger-

3. Run the application using Maven:

    ./mvnw spring-boot:run


4. Access the application at:

    "http://localhost:8080"
   
5. Usage Examples:
   
- List all posts:  [http://localhost:8080/posts](http://localhost:8080/posts);
   
- Get details of a post: [http://localhost:8080/posts/{id}](http://localhost:8080/posts/{id});


   


