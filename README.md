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

### Why you should avoid returning lists in APIs?
Returning a raw list in a REST API response lacks metadata, making it harder to include pagination, status codes, or error handling. Wrapping the list in an object ensures consistency, scalability, and flexibility for future enhancements.
Imagine you want to add a new property, like `totalItems`, to track how many items are in the list. If you return just a list, there's no easy way to include this property:
```json
[
  { "id": 1, "name": "Item 1" },
  { "id": 2, "name": "Item 2" }
]
```
**Recommended**: Instead, the response can be wrapped in an object that includes metadata along with the data:
```json
{
  "status": "success",
  "data": [
    { "id": 1, "name": "Item 1" },
    { "id": 2, "name": "Item 2" }
  ],
  "totalItems": 2
}
```

------------------------------------------------------------------------------------------------------------------------
### What are we doing wrong here?
So far we were communicating microservices via RestTemplate and WebClient and hardcoding API URLs

### Why Hardcoded URLs are bad?
- Changes require code updates
- Dynamic URLs in the cloud -> When we deploy microservices on cloud for example Heroku, then we don't have any idea
  what would be the API URLs
- Load Balancing
- Multiple environments -> In real-world applications, it's common to deploy across multiple environments 
  (e.g., development, staging, production). Hardcoding URLs makes it difficult to handle different 
  environments for several reasons

So, because of all these drawbacks, we have `Service Discovery (A Pattern)` which helps microservices discover and talk to each other

### Service Discovery in Spring Cloud

![Alt text](images/service-discovery-daigram.png)

Service discovery in Spring Cloud plays a crucial role in systems following a microservices architecture, as shown in the diagram. Here’s how it works:

- **Discovery Server (Registry)** : At the heart of service discovery is a discovery server, like **Eureka** (a widely-used component in Spring Cloud). This server acts as a registry where all the microservices register themselves. It's like a phonebook for services.

- **Service Registration** : Each microservice (such as **SRV 1**, **SRV 2**, and **SRV 3** in the diagram) registers its details (like hostname, port, etc.) with the discovery server. This allows other services to locate it dynamically rather than relying on hardcoded configurations.

- **Client-Side Service Discovery** : When the client (in this case, the **CLIENT** box in the diagram) needs to communicate with a specific service, it queries the discovery server to get the relevant information. This ensures flexibility and scalability, as services can be added or removed without affecting the client directly.

- **Load Balancing** : If multiple instances of a service exist (e.g., several instances of **SRV 1**), the discovery server can provide the client with all available instances. Tools like **Ribbon** (Spring Cloud Load Balancer) help to distribute requests across these instances efficiently.

- **Dynamic Updates** : The registry is updated dynamically, so if a service goes down or is brought up, the discovery server maintains an accurate and current list of available services.

Now, what happens in the above diagram for `Client Side Service Discovery`, each of those `services` which want to be discovered by the `Client`, register themselves with that `Discovery Server`.
In other words, `Service Discovery` is like a phone book guy which is maintaining a `Phone Book` and all these people are kind of 
providing the entries into the phone book. Whoever needs to make a call looks up the phone book and gets the address

`Remember : Spring Cloud uses the Client Side Service Discovery`

### What's Eureka Server?
Eureka Server is like the “address book” for microservices in Spring Cloud applications. Imagine you're building an online food delivery app with multiple services like:
- Order Service: Handles placing and tracking food orders.
- Restaurant Service: Manages restaurant menus and availability.
- Delivery Service: Coordinates with delivery agents.

Each service runs independently, but they need to talk to each other. Instead of hardcoding their locations (like IP addresses or URLs), which is unreliable in a dynamic environment, they can use Eureka Server to discover each other.

How it Works:
- Service Registration: All microservices (Order, Restaurant, Delivery) register themselves with Eureka Server when they start up. Eureka Server saves their details (hostnames, ports, etc.).
- Service Discovery: When one service (e.g., Order Service) wants to talk to another (e.g., Delivery Service), it asks Eureka Server for the latest location instead of relying on a hardcoded address.

## ⚠️ Fault Tolerance in Spring Microservices

Fault tolerance = Keeping your system **alive and responsive**, even when parts of it fail.

### 🧰 Key Tools (with Resilience4j):

- **Circuit Breaker** 🧯  
  Stops calling a failing service to avoid making things worse. Falls back to defaults.

- **Retry** 🔁  
  Tries a few times before giving up. Useful for temporary network hiccups.

- **Rate Limiter** 🚦  
  Controls traffic to prevent service overload.

- **Bulkhead** 🛡️  
  Isolates resources (like thread pools) to prevent a failure from sinking the entire system.

> 💡 Think of it as airbags for your microservices!

---

## 🧭 When Eureka Server Goes Down

### 😱 What breaks?
- ❌ New services can't register
- ❌ New services can't discover others (if not cached)
- ❌ No health checks or heartbeats

### 😌 What still works?
- ✅ Already-discovered services can still talk using cached registry
- ✅ Temporary survival thanks to local cache

### 🛠️ How to reduce impact:
- 🔁 Use **multiple Eureka servers** for high availability
- 🧠 Leverage **self-preservation mode** to avoid accidental instance removal
- 💾 Keep **registry fetch interval** short for fresher local cache

> 💬 Eureka down ≠ total blackout — just don't stay in single-server mode!

> # 🚀 Spring Boot Microservices - Fault Tolerance & Resilience (Level 2)

## 🎯 Workshop Focus

Welcome to **Level 2** of microservices with Spring Boot — where we focus on making systems **fault-tolerant** and **resilient**.

> "Before you learn a tool, understand the **problem** it solves."

This session is all about the **"why"** behind resilience, not just the "how."

---

## 🔍 Challenges with Availability

Microservices bring scalability and modularity, but also create **fragile interdependencies**.

### 🧠 What can go wrong?
- One service failing might **impact many others**
- Delays in one part may **slow down the entire system**
- **Unexpected downtime** or **network glitches** are inevitable

> Real-world example:  
> In a ride-hailing app, if the location service is down, should the app crash — or still let users browse?

---

## ⚠️ Why Do We Need Fault Tolerance?

Microservices need to handle failure **gracefully** to:
- Improve **system availability**
- Prevent **cascading failures**
- Maintain a **good user experience** even when things break

### Key Insight:
> You can't **prevent** all failures, but you can design systems that **recover from them**.

---

## 🧠 Resilience — It's a Mindset

This workshop emphasizes:
- Understanding **core resilience principles**
- Concepts that are **framework-agnostic**
- Patterns that remain **relevant**, whether you're using Spring Boot, Node.js, or Go

---

## 📦 Tech Stack (Used in this Workshop)

We'll be using:
- **Spring Boot** for microservices
- **Hystrix** (as an example library — focus is on concept, not the tool)

But remember — the goal is to **understand resilience**, not just implement Hystrix.

---

### What is Fault Tolerance?
> Given an application, if there's a fault; what is the impact of that fault? How much `Tolerance` does the system
have for a specific fault? Let's say, if one microservices instances were to go down, what's gonna to happen with your microservices'
application? Is your whole microservices application go down? Or is it a part of that application functionality that goes down?
Or we have some way of handling failures, so that there's no perceived impact at all on our microservices' application?

> So, in nutshell, what tolerance your system has for a particular fault is what's called `Fault Tolerance`

### What is Fault Resilience?
> `Resilience` is basically how many faults a system can tolerate and that indicates how resilient the system is. The first one you're looking at single fault 
> and saying, how much system can tolerate a single fault? And `Resilience` is how many faults can system tolerate before its brought down to its knees?
> `A part of resilience is how many times a system can bounce back  from a fault?`

> So, very similar concepts but slight differences. `You can technically have a system that's very fault tolerant but it's not resilient
> but its hard because most of the time these two go to gather and it's good to focus on the both.`

