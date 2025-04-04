# Schedule

This project is to study how to use Spring Boot based on JPA. Many people can save their info and schedule with their email and password. You can also save comments of each schedule.
Login is required to access on this service. Passwords are encoded with session.

## ERD
![img.png](ERD.png)

## REST API Specification

### Authors API

| Method | Endpoint        | Request Body                                                                                         | Response Body                                                    | Status        |
|--------|-----------------|------------------------------------------------------------------------------------------------------|------------------------------------------------------------------|---------------|
| POST   | /authors/signup | {"username":name of the user, "email":email, "password":password,"age":age}                          | {"id":id, "username":name of the user, "email":email, "age":age} | 200, 409      |
| GET    | /authors/{id}   | None                                                                                                 | {"username":name of the user, "age":age, "email":email}          | 200, 204      |
| PATCH  | /authors/{id}   | {"oldPassword":password before,"newPassword":password after, "username":name of the user, "age":age} | None                                                             | 200, 401, 409 |

### Schedules API

| Method | Endpoint        | Request Body                                                         | Response Body                                                                       | Status        |
|--------|-----------------|----------------------------------------------------------------------|-------------------------------------------------------------------------------------|---------------|
| POST   | /schedules      | {"title":title, "contents":contents, "scheduledDate":Scheduled Date} | {"id":id, "title":title, "contents":contents, "scheduledDate":Scheduled Date}       | 200           |
| GET    | /schedules      | None                                                                 | Page<{"id":id, "title":title, "contents":contents, "scheduledDate":Scheduled Date}> | 200           |
| GET    | /schedules/{id} | None                                                                 | {"id":id, "title":title, "contents":contents, "scheduledDate":Scheduled Date}       | 200, 204      |
| PATCH  | /schedules/{id} | {"title":title, "contents":contents, "scheduledDate":Scheduled Date} | None                                                                                | 200, 204, 403 |
| DELETE | /schedules/{id} | None                                                                 | None                                                                                | 200, 204, 403 |

### Login API

| Method | Endpoint  | Request Body       | Response Body   | Status |
|--------|----------|--------------------|----------------|--------|
| GET    | /home    | None               | HTML Page      | 200    |
| POST   | /login   | LoginRequestDto    | Redirect       | 200    |
| POST   | /logout  | None               | Redirect       | 200    |

### Comments API

| Method | Endpoint                              | Request Body          | Response Body                                                                                       | Status        |
|--------|---------------------------------------|-----------------------|-----------------------------------------------------------------------------------------------------|---------------|
| POST   | /schedules/{scheduleId}/comments      | {"comments":comments} | {"id":id of the comment, "comments":comment, "scheduledId":id of the schedule, "email":email}       | 200, 204      |
| GET    | /schedules/{scheduleId}/comments      | None                  | List<{"id":id of the comment, "comments":comment, "scheduledId":id of the schedule, "email":email}> | 200           |
| PATCH  | /schedules/{scheduleId}/comments/{id} | {"comments":comments} | None                                                                                                | 200, 204      |
| DELETE | /schedules/{scheduleId}/comments/{id} | None                  | None                                                                                                | 200, 204, 403 |


### Author, Schedule, and Comment Relationship
- Implemented `author` table to separate author details.
- Authors have unique identifiers (IDs), and schedules reference them via `id` of `author`.
- Modified query logic to search schedules by `id` of `author` and `comment`.


---

## Developer
- Developer: Kibeom Park
- Email: kibeom0806@gmail.com