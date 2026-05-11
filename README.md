# 🎬 Letterboxd Clone REST API

A robust, production-ready RESTful API built with **Java** and **Spring Boot**, replicating the core backend functionalities of a movie review platform like Letterboxd.

This project demonstrates advanced backend architecture, including stateless JWT security, role-based access control, external API integrations, and robust relational database management.

## 🚀 Key Features

* **Advanced Security:** Stateless Authentication and Authorization using **JSON Web Tokens (JWT)**. Custom entry points and access denial handlers for clean HTTP 401 and 403 responses.
* **Role-Based Access Control (RBAC):** Strict separation of concerns between `USER` and `ADMIN` roles.
* **External API Integration:** Seamless integration with the **TMDB (The Movie Database) API** via `RestTemplate` to fetch and persist real-world movie data.
* **Resource Ownership Validation:** Users can only update or delete their own reviews. Unauthorized attempts are blocked at the service layer.
* **Data Transformation:** Efficient Entity-to-DTO mapping utilizing **MapStruct**.
* **Pagination:** Optimized database queries using Spring Data JPA `Pageable` for fetching large lists of user reviews.
* **Global Exception Handling:** Centralized `@RestControllerAdvice` to catch exceptions (e.g., ResourceNotFound, UnauthorizedAccess) and return standardized JSON error responses.

## 🛠️ Tech Stack

* **Language:** Java 21+
* **Framework:** Spring Boot 3.x
* **Security:** Spring Security & JWT (jjwt)
* **Database:** PostgreSQL
* **ORM:** Hibernate / Spring Data JPA
* **Mapping:** MapStruct
* **Build Tool:** Maven

## 📋 API Endpoints Overview

Here is a quick glance at the primary endpoints. *(All endpoints except Auth and Movie searches require a valid JWT Bearer Token).*

| HTTP Method | Endpoint | Description | Role Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/users/register` | Register a new user | *None* |
| `POST` | `/api/v1/users/login` | Authenticate and receive JWT | *None* |
| `GET` | `/api/v1/users/me` | Get current user profile details | `USER` / `ADMIN` |
| `GET` | `/api/v1/users/me/reviews` | Get paginated reviews of current user | `USER` / `ADMIN` |
| `POST` | `/api/v1/movies/tmdb/{tmdbId}` | Fetch movie from TMDB and save to DB | `ADMIN` |
| `DELETE` | `/api/v1/movies/{id}` | Delete a movie (Cascades reviews) | `ADMIN` |
| `POST` | `/api/v1/reviews/{movieId}` | Add a review to a movie | `USER` / `ADMIN` |
| `PUT` | `/api/v1/reviews/{id}` | Update an existing review | *Review Owner* |
| `DELETE` | `/api/v1/reviews/{id}` | Delete a review | *Review Owner* |

## ⚙️ Setup and Installation

**1. Clone the repository:**
```bash
git clone [https://github.com/yourusername/letterboxd-clone-api.git](https://github.com/yourusername/letterboxd-clone-api.git)