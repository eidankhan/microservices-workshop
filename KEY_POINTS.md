# Microservices Overview
------------------------------------------------------------------------------------------------------------------------
- The fundamental idea is to develop applications in a modern way.
- Traditionally, applications are developed using a single codebase or multiple codebases, but what gets deployed is one entity on a single server.
- The primary difference between **monolithic** and **microservices** architecture:
    - **Monolithic**: Deployed as a whole entity on a single server.
    - **Microservices**: Each codebase is deployed on different servers.
- In other words, while coding patterns remain similar, the deployment approach is significantly different.
- There are many ways to develop microservices, and **Spring Cloud** is just one of the many technologies available for creating microservices.
- Each technology follows certain architectural **patterns**.
- The biggest challenge in developing microservices is their **interdependency**.
- Microservices solve generic problems like **load balancing**, which is why multiple technologies exist in the market—each solving a distinct problem.

### Audience Question: What is an example of a pattern and technology? What is the difference?

**Answer:**  
When an application is broken down into different services, different **patterns** are required for microservices to communicate with each other.
A good example is **Service Discovery**, which helps discover services and facilitates communication in a microservices architecture.
On the other hand, **technology** refers to tools or frameworks that implement these patterns. For example, **Eureka** is a technology that follows the Service Discovery pattern.

------------------------------------------------------------------------------------------------------------------------

- **@Bean** is the producer (registerer) while **@Autowired** is the consumer / subscriber 
- **@Bean** by default creates singleton object for each spring bean
- **RestTemplate** is an inbuilt way of calling microservices in spring framework
- **WebClient** is an alternative way of RestTemplate and RestTemplate has been deprecated long time ago.
  WebClient comes with `WebFlux` dependency and is an asynchronous way of interacting with microservices

### What is Mono?

#### Conceptual Definition
**"Mono is a reactive type in Spring WebFlux that represents a stream of zero or one element, enabling asynchronous and non-blocking programming."**

####  Practical Definition
**"In WebFlux, when making an API call like `webClient.get().retrieve().bodyToMono(Movie.class)`, it returns a `Mono<Movie>`, meaning the movie data will be available asynchronously when subscribed to
and can be used later once available."**
