# BankAccount System — README

**Project:** Spring Boot Bank Account System  
**Features:** account creation, deposits, withdrawals, transfers, transaction tracking, customer management, DTOs, validation, exception handling, REST best-practices, JPA repositories, sample H2/MySQL configs.

---

## Table of Contents
1. [Quick Start](#quick-start)
2. [Prerequisites](#prerequisites)
3. [Build & Run](#build--run)
4. [Configuration](#configuration)
5. [API Endpoints (examples)](#api-endpoints-examples)
6. [Entities & DTOs (overview)](#entities--dtos-overview)
7. [Validation & Error Handling](#validation--error-handling)
8. [Repository / Custom Methods](#repository--custom-methods)
9. [Transactions & Concurrency](#transactions--concurrency)
10. [Testing](#testing)

---

## Quick Start

Clone the repo and run with the default in-memory H2 DB:

```bash
git clone <repo-url>
cd bank-account-system
./mvnw clean package
./mvnw spring-boot:run
```

After startup the API will be available at http://localhost:8080.

H2 Console (default): http://localhost:8080/h2-console
JDBC URL (H2 default): jdbc:h2:mem:bankdb
Username: sa Password: (empty)

---

## Prerequisites
- Java 17+ (or the project's configured Java 
- Maven (or use the included Maven Wrapper `./mvnw`)
- Optional: MySQL server (if you want persistent storage)
- Recommended: Postman or curl for testing

---

## API Documentation
Explore the API documentation at http://localhost:8080/swagger-ui.html

---

## Project Structure

```
└── 5xgeorgex5-sprints_project-3/
    ├── README.md
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/
    │   │   │       └── team2/
    │   │   │           └── bank/
    │   │   │               ├── BankApplication.java
    │   │   │               ├── Config/
    │   │   │               │   └── ModelMapperConfig.java
    │   │   │               ├── Controllers/
    │   │   │               │   ├── BankAccountController.java
    │   │   │               │   ├── CustomerController.java
    │   │   │               │   └── TransactionController.java
    │   │   │               ├── DTOs/
    │   │   │               │   ├── BankAccountDTO.java
    │   │   │               │   ├── CustomerDTO.java
    │   │   │               │   └── TransactionDTO.java
    │   │   │               ├── Enums/
    │   │   │               │   └── TransactionType.java
    │   │   │               ├── Mapper/
    │   │   │               │   └── DTOMapper.java
    │   │   │               ├── Models/
    │   │   │               │   ├── BankAccountModel.java
    │   │   │               │   ├── CustomerModel.java
    │   │   │               │   └── TransactionModel.java
    │   │   │               ├── Repositories/
    │   │   │               │   ├── BankAccountRepo.java
    │   │   │               │   ├── CustomerRepo.java
    │   │   │               │   └── TransactionRepo.java
    │   │   │               └── Services/
    │   │   │                   ├── BankAccountService.java
    │   │   │                   ├── CustomerService.java
    │   │   │                   └── TransactionService.java
    │   │   └── resources/
    │   │       └── application.properties
    │   └── test/
    │       └── java/
    │           └── com/
    │               └── team2/
    │                   └── bank/
    │                       └── BankApplicationTests.java
    └── .mvn/
        └── wrapper/
            └── maven-wrapper.properties
```

## Build & Run

### Using embedded H2 (default)
```bash
./mvnw clean package
# run with the jar built by your project (replace with your actual jar file name)
java -jar target/<your-artifact>- .jar
# or
./mvnw spring-boot:run
```

### Using MySQL
- Create a database (example: `bankdb`)
- Set environment variables or modify `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bankdb?useSSL=false&serverTimezone=UTC
spring.datasource.username=<db-username>
spring.datasource.password=<db-password>
spring.jpa.hibernate.ddl-auto=update
```
- Run the app as above

---

## Configuration
Two example `application.properties` profiles are provided in `src/main/resources`:

- `application.properties` — default (H2 in-memory)
- `application-mysql.properties` — example for MySQL

### Activate profile:
- Unix/maxOS
```bash
SPRING_PROFILES_ACTIVE=mysql ./mvnw spring-boot:run
```

- Windows PowerShell:

```bash
$env:SPRING_PROFILES_ACTIVE="mysql"; ./mvnw spring-boot:run
```

---

## API Endpoints (examples)

Base URL: http://localhost:8080
All endpoints return JSON and follow REST convention

---

## Customers
### Create customer
`POST /customers` //
`http://localhost:8080/customers/create`

Request:

```json
{
  "name": "kadyy",
  "email": "kadyy@gmail.com"
}
```

Response:

```json
{
  "id": 1,
  "name": "kadyy",
  "email": "kadyy@gmail.com",
  "bankAccount": null
}
```

### Get customer by id
`GET /customers/{id}` // `http://localhost:8080/customers/1`

Response:

```json
{
  "id": 1,
  "name": "kadyy",
  "email": "kadyy@gmail.com",
  "bankAccount": null
}
```

### Get customer by email
`GET /customers/search` // `http://localhost:8080/customers/search?email=kadyy%40gmail.com`

Response:

```json
{
  "id": 1,
  "name": "kadyy",
  "email": "kadyy@gmail.com",
  "bankAccount": null
}
```

### Get all customers
`GET /customers/all` // `http://localhost:8080/customers/all`

Response:

```json
[
  {
    "id": 1,
    "name": "kadyy",
    "email": "kadyy@gmail.com",
    "bankAccount": {
      "id": 1,
      "balance": 6000,
      "accountType": "CURRENT",
      "customerId": 1
    }
  },
  {
    "id": 2,
    "name": "yasmine",
    "email": "yasmine@gmail.com",
    "bankAccount": {
      "id": 2,
      "balance": 10000,
      "accountType": "SAVINGS",
      "customerId": 2
    }
  }
]
```

### Update customer
`PUT /customers/update/{id}`

### Delete customer
`DELETE /customers/{id}`

---

## Bank Accounts
Note: In the current model, each customer is associated with a single bank account (one-to-one).

### Create account
`POST /accounts/create` // `http://localhost:8080/accounts/create`

Request:

```json
{
  "balance": 5000,
  "accountType": "SAVINGS",
  "customerId": 1
}
```

Response:

```json
{
  "id": 2,
  "balance": 5000,
  "accountType": "SAVINGS",
  "customerId": 1
}
```

### Get account by id
`GET /accounts/{id}` // `http://localhost:8080/accounts/2`

Response:

```json
{
  "id": 2,
  "balance": 5000,
  "accountType": "SAVINGS",
  "customerId": 1
}
```

### Get accounts by type
`GET /accounts/types/{type}` // `http://localhost:8080/accounts/types/SAVINGS`

Response:

```json
[
  {
    "id": 2,
    "balance": 10000,
    "accountType": "SAVINGS",
    "customerId": 2
  }
]
```

### Get accounts by balance range
`GET /accounts/balance/in-range` // `http://localhost:8080/accounts/balance/in-range?min=2000&max=7000`

Response:

```json
[
  {
    "id": 1,
    "balance": 6000,
    "accountType": "CURRENT",
    "customerId": 1
  }
]
```

`http://localhost:8080/accounts/balance/in-range?min=2000&max=10000`

Response:

```json
[
  {
    "id": 1,
    "balance": 6000,
    "accountType": "CURRENT",
    "customerId": 1
  },
  {
    "id": 2,
    "balance": 10000,
    "accountType": "SAVINGS",
    "customerId": 2
  }
]
```

### Get accounts greater than balance
`GET /accounts/balance/greater-than/{amount}` // `http://localhost:8080/accounts/balance/greater-than/6000`

```json
[
  {
    "id": 2,
    "balance": 10000,
    "accountType": "SAVINGS",
    "customerId": 2
  }
]
```

### Get all accounts
`GET /accounts/all` // `http://localhost:8080/accounts/all`

Response:

```json
[
  {
    "id": 1,
    "balance": 6000,
    "accountType": "CURRENT",
    "customerId": 1
  },
  {
    "id": 2,
    "balance": 10000,
    "accountType": "SAVINGS",
    "customerId": 2
  }
]
```

### Transfer between accounts
`POST /accounts/transfer` // `http://localhost:8080/accounts/2/transfer`

Request:

```json
{
  "receiverAccountId": "1",
  "amount": "2000"
}
```

`http://localhost:8080/customers/all`

Response:

```json
[
  {
    "id": 1,
    "name": "kadyy",
    "email": "kadyy@gmail.com",
    "bankAccount": {
      "id": 1,
      "balance": 8000,
      "accountType": "CURRENT",
      "customerId": 1
    }
  },
  {
    "id": 2,
    "name": "yasmine",
    "email": "yasmine@gmail.com",
    "bankAccount": {
      "id": 2,
      "balance": 8000,
      "accountType": "SAVINGS",
      "customerId": 2
    }
  }
]
```

### Withdraw from account
`POST /accounts/{id}/withdraw` // `http://localhost:8080/accounts/1/withdraw`

Request:

```json
{
  "amount": "2000"
}
```

Response:

```json
{
  "id": 1,
  "balance": 6000,
  "accountType": "CURRENT",
  "customerId": 1
}
```

### Deposit to account
`POST /accounts/{id}/deposit` // `http://localhost:8080/accounts/2/deposit`

Request:

```json
{
  "amount": "2000"
} 
```

Response:

```json
{
  "id": 2,
  "balance": 10000,
  "accountType": "SAVINGS",
  "customerId": 2
}
```

### Update account
`PUT /accounts/update/{id}` // `http://localhost:8080/accounts/update/2`

Request:

```json
{
  "balance": 6000,
  "accountType": "CURRENT",
}
```

Response:

```json
{
  "id": 2,
  "balance": 6000,
  "accountType": "CURRENT",
  "customerId": 1
}
```

### Delete account
`DELETE /accounts/{id}`

---

## Transactions
Transactions support types: `DEPOSIT`, `WITHDRAWAL`, `TRANSFER`.


Examples:

### List transactions (optional filter by account)
`GET /transactions`
```json
[
  {
    "id": 1,
    "accountOwner": {
      "id": 1,
      "name": "George",
      "email": "test@test.com",
      "bankAccount": {
        "id": 1,
        "balance": 50,
        "accountType": "SAVINGS",
        "customerId": 1
      }
    },
    "receiverAccount": {
      "id": 2,
      "balance": 150,
      "accountType": "SAVINGS",
      "customerId": 2
    },
    "amount": 50,
    "type": "TRANSFER"
  }
]
```

### Delete transaction
`DELETE /transactions/{id}`


---

## Enums, Entities, and DTOs (overview)

### ENUMs

- `BankAccountType` — CHECKING, SAVINGS, CURRENT

- `TransactionType` — DEPOSIT, WITHDRAWAL, TRANSFER

### Entities (conceptual)
- `Customer` — id, firstName, lastName, email, phone, bankAccount
- `BankAccount` — id, balance (double), accountType (BankAccountType), customer
- `Transaction` — id, account (or sender/receiver for transfers), type (TransactionType), amount (double), timestamp, description


### DTOs
- `CustomerDTO` — customer data; may include the associated bank account
- `BankAccountDTO` — account details with customerId reference
- `TransactionDTO` — transaction details for requests/responses

DTO mapping is handled in the service layer via a mapper component.

---

## Validation & Error Handling

- Request validation via annotations such as `@Valid`, `@NotNull`, `@Email`, `@Positive`, `@Size`
- Global exception handling via `@ControllerAdvice` (common status codes):
  - 400 for validation errors
  - 404 for not found
  - 409 for conflicts

### Error Handler

```java
@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC).toString());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC).toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
```

Example error:

```json
{
  "timestamp": "2025-08-10T06:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "balance must be positive",
  "path": "/accounts/1/"
}
```

---

## Repository / Custom Methods

Example:

```java
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByAccountType(String type);
    List<BankAccount> findByBalanceGreaterThan(Double amount);

    @Query("SELECT b FROM BankAccount b WHERE b.balance BETWEEN :min AND :max")
    List<BankAccount> findAccountsInRange(@Param("min") Double min, @Param("max") Double max);

    @Modifying
    @Query("UPDATE BankAccount b SET b.balance = b.balance + :delta WHERE b.id = :id")
    int updateAccountBalance(@Param("id") Long id, @Param("delta") Double delta);
}
```

---

## Transactions & Concurrency

- Business operations run within `@Transactional` service methods
- You may use `@Modifying` queries for direct updates or deletions where appropriate
- For higher concurrency, consider optimistic locking with `@Version` or explicit locking