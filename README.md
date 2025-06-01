# Spring Boot Demo: A Simple RESTful Web Service with Gradle

A Spring Boot application that demonstrates how to create a basic RESTful web service with endpoints for greeting users. The project showcases Spring Boot's auto-configuration capabilities and includes comprehensive testing infrastructure.

This project serves as a practical example of Spring Boot application development, featuring:
- RESTful endpoint implementation with Spring MVC
- Gradle-based build configuration and dependency management
- Comprehensive test coverage with unit and integration tests
- Spring Boot Actuator for application monitoring
- Java 24 toolchain support

## Repository Structure
```
spring-boot-demo/
├── build.gradle                 # Gradle build configuration with Spring Boot dependencies
├── gradle/wrapper/             # Gradle wrapper for consistent builds
├── src/
│   ├── main/
│   │   ├── java/              # Application source code
│   │   │   └── org/example/springbootdemo/
│   │   │       ├── Greeting.java           # Greeting data model
│   │   │       ├── GreetingController.java # REST controller for greeting endpoint
│   │   │       ├── HelloController.java    # REST controller with greeting endpoints
│   │   │       └── SpringBootDemoApplication.java  # Application entry point
│   │   └── resources/
│   │       └── application.properties  # Spring Boot configuration
│   └── test/
│       └── java/              # Test source code
│           └── org/example/springbootdemo/
│               ├── HelloControllerITest.java    # Integration tests
│               ├── HelloControllerTest.java     # Unit tests
│               └── SpringBootDemoApplicationTests.java  # Application context tests
```

## Usage Instructions
### Prerequisites
- Java Development Kit (JDK) 24 or later
- Gradle (optional, wrapper included)
- An IDE with Spring Boot support (recommended)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/bcguo/spring-boot-demo.git
cd spring-boot-demo
```

2. Build the project:
```bash
# For Unix-like systems
./gradlew build

# For Windows
gradlew.bat build
```

### Quick Start
1. Run the application:
```bash
./gradlew bootRun
```

2. Test the endpoints:
```bash
# Get default greeting
curl http://localhost:8080/

# Get personalized greeting
curl http://localhost:8080/hello?name=YourName

# Get JSON greeting
curl http://localhost:8080/greeting?name=YourName
```

### More Detailed Examples

1. Using the root endpoint:
```bash
curl http://localhost:8080/
# Response: "Greetings from Spring Boot!"
```

2. Using the hello endpoint with custom name:
```bash
curl http://localhost:8080/hello?name=John
# Response: "Hello, John!"
```

3. Using the hello endpoint with default value:
```bash
curl http://localhost:8080/hello
# Response: "Hello, World!"
```

4. Using the greeting endpoint for JSON response:
```bash
curl http://localhost:8080/greeting?name=John
# Response: {"id":1,"content":"Hello, John!"}
```

### Running on Local Minikube Kubernetes Cluster
1. Build the image
```bash
docker build -t spring-boot-demo .
```

2. Load the image into Minikube
```bash
docker save spring-boot-demo > spring-boot-demo.tar
minikube start
minikube image load spring-boot-demo.tar
# minikube image ls
# ...
# localhost/spring-boot-demo:local
```

3. Create the deployment
```bash
kubectl apply -f deployment.yaml
# deployment.apps/spring-boot-demo created
```

4. Retrieve the Minikube tunnel URL for testing
```bash
minikube service spring-boot-demo --url
```

### Troubleshooting

1. Application fails to start
- Check if port 8080 is already in use
```bash
# On Unix-like systems
lsof -i :8080
# On Windows
netstat -ano | findstr :8080
```
- Verify Java version:
```bash
java -version
```
- Enable debug logging by adding to application.properties:
```properties
logging.level.org.springframework=DEBUG
```

2. Build failures
- Clear Gradle cache:
```bash
./gradlew clean --refresh-dependencies
```
- Verify Gradle wrapper:
```bash
./gradlew wrapper --gradle-version 8.5
```

## Data Flow
The application follows a simple request-response flow for handling HTTP requests.

```ascii
Client Request         -> Controller -> Response
     │                                        │
     ├─► GET /                                │
     ├─► GET /hello    -> HelloController ────┤
     │                                        │
     └─► GET /greeting -> GreetingController ─┘
```

Component interactions:
1. Client sends HTTP GET request to either "/" or "/hello" endpoint
2. HelloController or GreetingController processes the request
3. For "/hello" endpoint, name parameter is extracted from query string
4. Controller generates appropriate greeting message
5. For "/greeting" endpoint, response includes a unique ID and message in JSON format
6. Response is serialized and sent back to client
7. Spring Boot Actuator monitors the request/response cycle