# Demo Transactions Service

Simple Spring Boot application with 3 endpoints:

- POST /accounts -> create account
- GET /accounts/{id} -> get account
- POST /transactions -> create transaction

Run locally:

```bash
./mvnw spring-boot:run
```

Or build and run:

```bash
./mvnw package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Tests:

```bash
./mvnw test
```

Docker (optional):

```bash
docker build -t demo-transactions .
docker run -p 8080:8080 demo-transactions
```

Notes:
- Uses H2 in-memory database.
- Operation types are pre-populated (1..4).
