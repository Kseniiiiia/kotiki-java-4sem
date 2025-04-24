# kotiki-java-4sem 🐱

REST API для работы с информацией о котиках и их владельцах на Spring Boot.

## Технологический стек

- Java 21
- Spring Boot 
- Spring Data JPA
- Hibernate
- PostgreSQL
- Flyway
- Swagger
- Lombok

## Функциональность API

### endpoint'ы:

- POST /owners/create
{
"ownerName": "Иван Иванов",
"ownerBirthDate": "1990-01-01"
}

- GET /owners/all

- GET /owners/get/1   

- PUT /owners/update/1
{
"ownerName": "Иван Иванов",
"ownerBirthDate": "2010-01-01"
}

- DELETE /owners/delete/1


- POST /pets/create
{
"petName": "Мурзик",
"petBirthDate": "2020-05-15",
"color": "WHITE",
"breed": "SIAMESE",
"owner": {
    "id": 1
    }
}

- GET /pets/all

- GET /pets/get/1

- GET /pets/getByColor/WHITE

- GET /pets/getByBreed/SIAMESE

- GET /pets/getByOwner/1

- PUT /pets/update/1
{
"petName": "Мурзик",
"petBirthDate": "2023-05-15",
"color": "BLACK",
"breed": "SIAMESE",
"owner": {
    "id": 1
    }
}

- DELETE /pets/delete/1


- POST /friends/create
{
"petId": 1,
"friendId": 2
}

- GET /friends/all

- GET /friends/exists?petId=1&friendId=2
