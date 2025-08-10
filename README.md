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
`POST /customers`

```json
{
  "firstName": "Ahmed",
  "lastName": "Ali",
  "email": "ahmed@example.com",
  "phone": "+20100..."
}
```

### Get customer by id
`GET /customers/{id}`

### List customers (paged)
`GET /customers?page=0&size=20`

### Update customer
`PUT /customers/{id}`

### Delete customer
`DELETE /customers/{id}`

---

## Bank Accounts
Note: In the current model, each customer is associated with a single bank account (one-to-one).

### Create account
`POST /accounts`

```json
{
  "customerId": 1,
  "accountType": "SAVINGS",
  "initialBalance": 1000.0
}
```

### Get account by id
`GET /accounts/{id}`

### List accounts (paged)
`GET /accounts?page=0&size=20`

### Update account
`PUT /accounts/{id}`

### Delete account
`DELETE /accounts/{id}`

---

## Transactions
Transactions support types: `DEPOSIT`, `WITHDRAWAL`, `TRANSFER`.

### Create transaction
`POST /transactions`

Examples:

### Deposit/Withdraw:

```json
{
    "accountId": 1,
    "type": "DEPOSIT",
    "amount": 250.0,
    "description": "Salary"
}
```

### Transfer:

```json
{
    "senderAccountId": 1,
    "receiverAccountId": 2,
    "type": "TRANSFER",
    "amount": 100.0,
    "description": "Rent share"
}
```

### List transactions (optional filter by account)
`GET /transactions?page=0&size=50`
`GET /transactions?accountId=1&page=0&size=50`

### Delete transaction
`DELETE /transactions/{id}`


---

## Entities & DTOs (overview)

### Entities (conceptual)
- `Customer` — id, firstName, lastName, email, phone, bankAccount
- `BankAccount` — id, balance (double), accountType (String), customer
- `Transaction` — id, account (or sender/receiver for transfers), type, amount (double), timestamp, description

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
  - 500 for server errors

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

---

## Testing

Run tests:

```bash
./mvnw test
```

---