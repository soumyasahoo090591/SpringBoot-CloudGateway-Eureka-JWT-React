
# Spring Boot API Gateway Demo
This project demonstrates the FSE assignment gateway using microservices architecture, separate authentication service and service discovery and UI with react SPA.

# Getting Started
* Run discovery-server, other services and UI
* Run API requests in request-examples.http

# Architecture
* Microservice architecture with SPA UI.

# Services
* **api-gateway**: Zuul edge service for routing 
* **discover-server**: Eureka server for service discovery
* **auth-service**: JWT authentication service
* **protected-service**: service with sensitive data
* **tweetApp** : few APIs to login, register and post/view tweets
