#  Waste Management API

## Overview
The Waste Management API is a Spring Boot application designed to promote sustainable waste management practices. This API can serve as part of backend for a mobile application that helps individuals and communities make environmentally conscious decisions about waste disposal and recycling.

The API allows users to:
- Access information about different waste categories
- Retrieve specific disposal guidelines for various types of waste
- Get recycling tips and best practices
- Search for waste management information
- Track disposal guidelines and recycling tips per category

---

## Technologies Used
- Java 17
- Spring Boot 3.4.1
- Spring Data JPA
- H2 Database
- JUnit 5 & Mockito for testing
- OpenAPI/Swagger for API documentation
- Maven for dependency management

## Getting Started

### Prerequisites
- Java JDK 17 or higher
- Maven 3.x.x versions 


### Build Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/NitionVR/waste-management-api.git
   ```
2. **Navigate to the project directory:**:
   ```bash
   cd waste-management-api
   ```
3. **Build the project:**:
   ```bash
   mvn clean install
   ```
4. **Run the application**:
   ```bash
   mvn spring-boot:run
   ``` 

The application will start on http://localhost:5000

## Testing

The project includes both unit and integration tests. Run tests with:
   ```bash
   mvn test 
   ``` 

### Test Coverage
- Controller layer/API endpoint tests
- Service layer tests
- Repository layer tests
- DTO conversion tests
- Integration tests

## Project Structure
```text
src/
├── main/
│   ├── java/
│   │   └── com/company_name/project_type/project/[name] (exact package omitted)/
│   │       ├── controllers/
│   │       ├── models/
│   │       ├── repositories/
│   │       ├── services/
│   │       └── dto/
│   └── resources/
│       └── application.yml
└── test/
    └── java/
        └── com/company_name/project_type/project_code/[name] (exact package omitted)/
            ├── controllers/
            ├── models/
            └── repositories/
            └── services/
            └── dto/
```            

## Features
- CRUD operations for waste categories, recycling tips, and disposal guidelines
- Search functionality
- Category-based organization of tips and guidelines
- API documentation
- Comprehensive testing

## Best Practices Implemented
- REST API design principles
- DTO pattern for data transfer
- Repository Pattern for data access
- Layered Architecture with MVC pattern
- Dependency Injection
- Proper error handling
- Input validation
- Proper documentation



## API Documentation
Once the application is running, access the Swagger UI documentation at:

   ```http
   http://localhost:5000/swagger-ui.html
   ``` 

### API Endpoints

#### Waste Categories

| HTTP Method | Endpoint                                   | Description                    |
|-------------|-------------------------------------------|--------------------------------|
| GET         | /wastemanagementapi/categories            | List all waste categories      |
| GET         | /wastemanagementapi/categories/{id}       | Get specific category          |
| POST        | /wastemanagementapi/categories            | Create new category            |
| PUT         | /wastemanagementapi/categories/{id}       | Update category                |
| DELETE      | /wastemanagementapi/categories/{id}       | Delete category                |
| GET         | /wastemanagementapi/categories/search     | Search categories              |

#### Recycling Tips

| HTTP Method | Endpoint                                                  | Description                    |
|-------------|----------------------------------------------------------|--------------------------------|
| GET         | /wastemanagementapi/categories/{categoryId}/tips          | Get tips for category          |
| POST        | /wastemanagementapi/categories/{categoryId}/tips          | Add tip to category            |
| GET         | /wastemanagementapi/tips/{id}                             | Get specific tip               |
| PUT         | /wastemanagementapi/tips/{id}                             | Update tip                     |
| DELETE      | /wastemanagementapi/tips/{id}                             | Delete tip                     |
| GET         | /wastemanagementapi/tips/search                           | Search tip                     |

#### Disposal Guidelines

| HTTP Method | Endpoint                                                  | Description                    |
|-------------|----------------------------------------------------------|--------------------------------|
| GET         | /wastemanagementapi/categories/{categoryId}/guidelines    | Get guidelines for category    |
| POST        | /wastemanagementapi/categories/{categoryId}/guidelines    | Add guideline to category      |
| GET         | /wastemanagementapi/guidelines/{id}                       | Get specific guideline         |
| PUT         | /wastemanagementapi/guidelines/{id}                       | Update guideline               |
| DELETE      | /wastemanagementapi/guidelines/{id}                       | Delete guideline               |
| GET         | /wastemanagementapi/guidelines/search                     | Search guideline               |


## API Documentation & Examples



### Waste Categories API

#### List All Categories
```http
GET /wastemanagementapi/categories
```


##### Response Example:

```json
[
  {
    "id": 1,
    "name": "Recyclable Materials",
    "description": "Materials that can be processed and reused",
    "guidelinesCount": 3,
    "tipsCount": 2
  },
  {
    "id": 2,
    "name": "Hazardous Waste",
    "description": "Materials requiring special handling",
    "guidelinesCount": 5,
    "tipsCount": 4
  }
]
```

#### Get Category by ID
```http
GET /wastemanagementapi/categories/{id}
```

##### Response Example:

```json
{
  "id": 1,
  "name": "Recyclable Materials",
  "description": "Materials that can be processed and reused",
  "guidelinesCount": 3,
  "tipsCount": 2
}
```

##### Create Category

```http
POST /wastemanagementapi/categories
```

##### Request Body Example:

```json
{
  "name": "Recyclable Materials",
  "description": "Materials that can be processed and reused"
}
```

##### Response Example:
```json
{
  "id": 1,
  "name": "Recyclable Materials",
  "description": "Materials that can be processed and reused",
  "guidelinesCount": 0,
  "tipsCount": 0
}
```

#### Update Category

```http
PUT /wastemanagementapi/categories/{id}
```

##### Request body Example: 

```json
{
  "name": "Updated Name",
  "description": "Updated description"
}
```

#### Delete category

```http
DELETE /wastemanagementapi/categories/{id}
```


####Search Categories

```
GET /wastemanagementapi/categories/search?keyword={keyword}
```

Query Parameters:
- `keyword` (optional): Search term for category names

##### Response Example:

```json
[
  {
    "id": 1,
    "name": "Recyclable Materials",
    "description": "Materials that can be processed and reused",
    "guidelinesCount": 3,
    "tipsCount": 2
  }
]
```

### Recycling Tips API

#### Get Tips for Category

```http
GET /wastemanagementapi/categories/{categoryId}/tips
```

##### Response Example:

```json
[
  {
    "id": 1,
    "title": "Paper Recycling",
    "content": "Separate different types of paper before recycling",
    "categoryId": 1,
    "categoryName": "Recyclable Materials"
  }
]
```

#### Create Tip
```http
POST /wastemanagementapi/categories/{categoryId}/tips
```
##### Request Body Example:
```json

{
  "title": "Paper Recycling",
  "content": "Separate different types of paper before recycling"
}
```

#### Update Tip
```http
PUT /wastemanagementapi/tips/{id}
```
##### Request Body Example:
```json
{
  "title": "Updated Title",
  "content": "Updated content"
}
```

### Disposal Guidelines API

#### Get Guidelines for Category

```http
GET /wastemanagementapi/categories/{categoryId}/guidelines
```

##### Response Example:

```json
[
  {
    "id": 1,
    "title": "Battery Disposal",
    "instructions": "Take to designated collection points",
    "categoryId": 2,
    "categoryName": "Hazardous Waste"
  }
]
```

#### Create Guideline

```http
POST /wastemanagementapi/categories/{categoryId}/guidelines
```

##### Request Body Example:

```json
{
  "title": "Battery Disposal",
  "instructions": "Take to designated collection points"
}
```

#### Update Guideline

```http
PUT /wastemanagementapi/guidelines/{id}
```

##### Request Body Example:

```json
{
  "title": "Updated Title",
  "instructions": "Updated instructions"
}
```

## Error Handling
The API uses standard HTTP status codes:

- 200: Success
- 201: Created
- 204: No Content (for successful deletions)
- 400: Bad Request
- 404: Not Found
- 500: Internal Server Error

  
Error Response Format:

```json
{
  "status": "error",
  "message": "Error description",
  "timestamp": "2025-01-15T10:15:30Z",
  "path": "/api/categories/999"
}
```

#### Features

- Global exception handling
- Meaningful error messages
- Appropriate HTTP status codes
- Input validation

## Validation Rules

### Categories:
- Name: Required, 3-50 characters
- Description: Optional, max 500 characters
### Recycling Tips:
- Title: Required, 3-100 characters
- Content: Required, 10-1000 characters
- Category: Must exist
### Disposal Guidelines:
- Title: Required, 3-100 characters
- Instructions: Required, 20-2000 characters
- Category: Must exist


## Future Improvements
- Authentication and Authorization
- Caching implementation
- Rate limiting
- Improved search functionality

## Contributing
This project is not open for contributions, although feedback is welcome.

## Contact
Amos Maganyane
--> kmaganyane83@gmail.com
 
   
