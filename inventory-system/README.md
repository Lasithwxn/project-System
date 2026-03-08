# Inventory & Stock Management System

This Java Spring Boot application implements a file-based inventory system with CRUD operations for items, users, and transaction history. It uses plain `.txt` files instead of a database and demonstrates OOP principles (encapsulation, inheritance, polymorphism).

## Setup
1. **Prerequisites**: Java 17, Maven, IntelliJ IDEA
2. Clone repository and open in IntelliJ.
3. Run `mvn clean install` to build.
4. Execute `InventoryApplication` or run `mvn spring-boot:run`.
5. Access `http://localhost:8080`.

## File structure
```
inventory-system/
├── src/main/java/com/inventory/
│   ├── model/         (BaseEntity, Item, User, Transaction)
│   ├── interfaces/    (Manageable)
│   ├── service/       (ItemService, UserService, TransactionService)
│   ├── controller/    (ItemController, UserController, TransactionController, DashboardController)
│   ├── util/          (FileHandler)
│   └── InventoryApplication.java
├── src/main/resources/
│   ├── templates/     (Thymeleaf HTML)
│   ├── static/css
│   ├── static/js
│   ├── data/          (.txt storage files)
│   └── application.properties
├── pom.xml
├── .gitignore
└── README.md
```

## Sample Git commands
```bash
git add .
git commit -m "Initialize inventory system project with models and services"
git push origin main
```

## Class diagram (text)
```
BaseEntity
  + id: String
  + createdAt: LocalDateTime
  + get/set
   ^
   | extends
Item, User, Transaction

Manageable<T> (interface)
  + save(T)
  + delete(String)
  + findById(String)
  + findAll()

ItemService implements Manageable<Item>
UserService implements Manageable<User>
TransactionService implements Manageable<Transaction>

FileHandler (utility) used by all services
Controllers depend on services (DashboardController, ItemController, etc.)
```

## Viva talking points
- **FileHandler**: explains read/write with `BufferedReader/Writer`, line-by-line updates.
- **Encapsulation**: models use private fields and getters/setters.
- **Inheritance**: BaseEntity with common properties, extended by Item/User/Transaction.
- **Polymorphism**: Manageable interface implemented by each service; each handles file I/O differently.
- **CRUD demo**: show create/list/update/delete items and verify file changes.
- **Login/session**: simple form handling; security stubbed by Spring Security.
- **Git history**: commit messages show stepwise development by team members.

For detailed deliverables and usage, refer to project documentation in repository.
