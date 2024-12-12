Project Deployment Description
The project was deployed using Docker on a DigitalOcean server. Below are the key elements and technologies involved in the deployment process:

Backend:

The backend application was built using Java Spring Boot.
The application was packaged into a JAR file using Maven (mvn clean package).
Containerization:

The JAR file was containerized using Docker.
A Dockerfile was created with the OpenJDK 17 base image:
The JAR file was copied into the container.
The application was executed using ENTRYPOINT with the java -jar command.
Deployment:

A DigitalOcean droplet (virtual server) was provisioned to host the application.
Docker Engine was installed on the server to run and manage containers.
The application container was started using:
```json
docker run -p 8080:8080 notes-api
```
Port 8080 on the server was exposed to allow external access to the application.
Networking:

The application is accessible through the server's public IP address on port 8080.

1. Base URL
The base URL for all requests will look like this:

```
http://64.226.67.155:8080
```
How can you use it in POSTMAN:
http://64.226.67.155:8080/api/v1/auth/register(register (POST method))
http://64.226.67.155:8080/api/v1/auth/login(login (POST method))
Enter the endpoint URL (http://64.226.67.155:8080/api/entries)
(you need to use your access token)
POST	/api/entries	Create a new entry	{ "title": "Test", "content": "...", "image":"..." }
GET	/api/entries	Retrieve all entries	No body needed
GET	/api/entries/{id}	Retrieve an entry by ID	Replace {id} with entry ID
PUT	/api/entries/{id}	Update an existing entry	{ "title": "Updated", "content": "..." }
DELETE	/api/entries/{id}	Delete an entry by ID	No body needed
