# To Do App

### Reasoning behind the technologies used
For this project I used the following technologies, all of which I have experience with:
- **Java Spring Boot**: I chose Spring Boot because I have worked with it before and I felt comfortable using it. It is a powerful framework that allows me to create a REST API effectively.
- **Flyway**: I used Flyway to manage the database migrations. It allows me to keep track of the changes in the database schema.
- **PostgreSQL**: I chose PostgreSQL as the database because it is a powerful and reliable database that has good performance.
- **Docker**: I used Docker to containerize the application. It allows me to run the application in a container and deploy it quickly.
- **Quasar**: I chose Quasar, a framework with VueJS components, to create the frontend. It helped me to create a responsive and modern user interface.

### How to run the application
To run the application, you need to have Docker installed on your machine. Clone the repository, move into the project folder and build and start the containers:

```bash
// Clone the repository
git clone https://github.com/KMKgit/ToDoApp.git

// Move into the project folder
cd ToDoApp

// Build and start the containers
docker compose up -d
```

If all containers have started successfully, you can access the application at http://localhost:9000.
