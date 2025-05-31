# Todo Service

A Spring Boot-based Todo list management service with MySQL database integration, containerized with Docker.

## Features

- Create, read, update, and delete todos
- Subtasks support for each todo
- RESTful API endpoints
- Containerized with Docker and Docker Compose
- Database migrations with Liquibase
- Health check endpoints

## Prerequisites

- Docker and Docker Compose installed on your system
- Java 17 or higher (for local development without Docker)
- Maven (for local development without Docker)

## Getting Started

### Using Docker Compose (Recommended)

1. Clone the repository:
   ```bash
   git clone https://github.com/Manju-Veerla/TodoService.git
   cd TodoService
   ```

2. Start the services:
   ```bash
   docker-compose up --build
   ```

3. The application will be available at: http://localhost:8080

### Without Docker

1. Make sure you have MySQL running locally
2. Update the database configuration in `src/main/resources/application.yaml`
3. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

- `GET /api/todos` - Get all todos
- `POST /api/todos` - Create a new todo
- `GET /api/todos/{id}` - Get a specific todo
- `PUT /api/todos/{id}` - Update a todo
- `DELETE /api/todos/{id}` - Delete a todo
- `GET /actuator/health` - Application health check

## Database

The application uses MySQL 8.1.0 with the following default credentials:
- Database: `todo_db`
- Username: `admin`
- Password: `admin`

## Project Structure

```
TodoService/
├── src/
│   ├── main/
│   │   ├── java/com/example/task/
│   │   │   ├── controller/     # REST controllers
│   │   │   ├── model/          # Entity and DTO classes
│   │   │   ├── repository/     # Data access layer
│   │   │   ├── service/        # Business logic
│   │   │   └── TodoServiceApplication.java
│   │   └── resources/
│   │       ├── db/changelog/    # Database migrations
│   │       └── application.yaml # Application configuration
├── docker-compose.yml           # Docker Compose configuration
├── Dockerfile                   # Application Dockerfile
├── init.sql                    # Database initialization script
└── pom.xml                     # Maven configuration
```

## Environment Variables

The following environment variables can be configured:

- `SPRING_DATASOURCE_URL`: JDBC URL for the database (default: jdbc:mysql://mysql-service:3306/todo_db)
- `SPRING_DATASOURCE_USERNAME`: Database username (default: admin)
- `SPRING_DATASOURCE_PASSWORD`: Database password (default: admin)

## Troubleshooting

### Common Issues

1. **Database Connection Issues**:
   - Make sure MySQL is running and accessible
   - Check the database credentials in `docker-compose.yml` and `application.yaml`
   - Run `docker-compose logs mysql-service` to check MySQL logs

2. **Port Conflicts**:
   - The application runs on port 8080 by default
   - MySQL runs on port 3307 (mapped from container's 3306)

3. **Build Issues**:
   - Run `docker-compose build --no-cache` to rebuild the images without cache

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
