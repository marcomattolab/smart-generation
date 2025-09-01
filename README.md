# 🚀 Smart Generation 🚀

This project is a powerful full stack webcode generator that scaffolds a complete full-stack application, including a web frontend, a backend and a full CI/CD pipeline with Docker. It's designed to accelerate development by automating the creation of boilerplate code and project structure based on a simple JSON configuration.

## ✨ Features

The generator creates a multi-platform application with a rich set of features out of the box.

### 🌐 Frontend (Angular)

- **Modern Angular Setup:** Standalone components, SCSS styling, and the new built-in control flow syntax (`@for`, `@if`).
- **State Management with Signals:** Uses Angular's built-in Signals for a simple, modern, and reactive state management architecture.
- **Component Library:** PrimeNG for a rich set of UI components.
- **Styling:** Tailwind CSS for utility-first styling.
- **API Integration:** Generates signal-based services for all entities to communicate with the backend.
- **Development Proxy:** Configures a proxy to the backend to avoid CORS issues.
- **Internationalization (i18n):** Uses `@ngx-translate` for multi-language support.
- **Testing:**
    - **Cypress:** For end-to-end testing.
    - **Storybook:** For component visualization and testing.
    - **Component Tests:** Generates basic `.spec.ts` files for all components.
- **Documentation:** Configured with Compodoc for generating project documentation.

### ⚙️ Backend (Spring Boot)

- **Build System:** Maven for dependency management.
- **Database:** PostgreSQL for robust data persistence.
- **API:** Generates a complete RESTful API for all entities.
- **Architecture:**
    - **DTO Pattern:** Decouples the API from the database entities.
    - **Service Layer:** For business logic.
- **ORM:** Spring Data JPA for data access.
- **Code Quality:** Uses Lombok to reduce boilerplate code.
- **Monitoring:** Exposes metrics for Prometheus via Spring Boot Actuator.


### 🐳 CI/CD (Docker)

- **Containerization:** Generates multi-stage `Dockerfile`s for the frontend and backend to produce lean, production-ready images.
- **Orchestration:** Creates a `docker-compose.yml` file to manage the entire application stack.
- **Services:**
    - **Nginx:** Acts as a reverse proxy for the frontend and backend.
    - **PostgreSQL:** The application database.
    - **MinIO:** S3-compatible object storage.
    - **Prometheus:** For collecting application metrics.
    - **Grafana:** For visualizing metrics, pre-configured with Prometheus as a data source.

## 🚀 How It Works

The project reads two main configuration files:

- `project.json`: Defines the basic project structure (e.g., app names, package names)and the list of entities and their properties, which drives the code generation for all platforms.

The `App.Java` script orchestrates generators.

### Prerequisites

- Smart Generation uses Java 17
- Docker and Docker Compose
- Node.js and npm (for local frontend development)
- Java and Maven (for local backend development)

## ▶️ How to Run - Generate the Application

1.  Customize `project.json` to define your application.
2.  Run the generator:
    ```bash
    mvn clean install
    java App
    ```
    This will create a new directory (specified by the `name` in `project.json`, e.g., `generated_app/`) containing the complete project.

### Running the Full Stack with Docker

Navigate to the generated project directory and run:
```bash
docker-compose up --build
```
This will build the Docker images and start all the services. 
The application will be available at `http://localhost:8080`