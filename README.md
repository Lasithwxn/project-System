# InvTrack - Inventory & Stock Management System

A complete Inventory and User Management System built with **Java Spring Boot**, **JSP/Servlets**, and **Bootstrap 5**. This project demonstrates core Object-Oriented Programming (OOP) concepts, CRUD operations, and file-based data storage.

## Features

- **User Authentication**: Login and Logout functionality with session management.
- **Role-Based Access Control**:
  - **ADMIN**: Full access to dashboard, items, and user management.
  - **MANAGER/STAFF**: Access to dashboard and items only.
- **CRUD Operations**:
  - Create, Read, Update, and Delete for both **Users** and **Inventory Items**.
  - **Deactivation**: Users can be deactivated instead of deleted to maintain records.
- **File-Based Storage**: Data is stored in `.txt` files in CSV format (no database required).
- **Responsive UI**: Built with Bootstrap 5 and Bootstrap Icons for a modern, clean interface.

## OOP Concepts Applied

1.  **Encapsulation**:
    -   Implemented in `User.java` and `Item.java` using private fields and public getters/setters with validation logic.
2.  **Inheritance**:
    -   `Item.java` is an abstract base class.
    -   `Product.java` and `StockAlert.java` inherit from `Item.java`.
3.  **Polymorphism**:
    -   **Method Overloading**: `searchUsers()` and `searchItems()` methods in service classes are overloaded to support different search parameters. `writeToFile()` in `FileService` is also overloaded.
    -   **Method Overriding**: `toCSV()` method is overridden in `Product.java` and `StockAlert.java`.
    -   **Upcasting**: Lists of type `List<Item>` can hold both `Product` and `StockAlert` objects.
4.  **Abstraction**:
    -   `Item.java` is defined as an `abstract` class to provide a template for all inventory items without being instantiated directly.
5.  **Information Hiding**:
    -   Data access is restricted through the Service layer (`UserService`, `ItemService`), hiding the complexity of file I/O from the UI/Servlet layer.

## Project Structure

```text
inventory-management/
├── src/main/java/com/inventory/
│   ├── model/         # OOP Model classes
│   ├── service/       # Business logic & File handling
│   ├── servlet/       # HTTP request handling
│   ├── filter/        # Security & RBAC
│   ├── util/          # Helper utilities
│   └── InventoryApplication.java
├── src/main/webapp/WEB-INF/jsp/  # UI Views
├── data/              # File-based storage (.txt)
├── pom.xml            # Maven configuration
└── README.md
```

## Setup and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- IDE (IntelliJ IDEA recommended)

### Running the Application
1.  Open the project in your IDE as a Maven project.
2.  Run the `InventoryApplication.java` file.
3.  Access the application at: `http://localhost:8080`

### Default Login Credentials
| Role    | Username | Password |
|---------|----------|----------|
| ADMIN   | admin    | admin123 |
| MANAGER | john     | john123  |
| STAFF   | sara     | sara123  |

## Implementation Details
- **File Storage**: Uses `BufferedReader` and `BufferedWriter` via `java.nio.file.Files` for efficient reading and writing.
- **Error Handling**: All file operations are wrapped in `try-catch` blocks to ensure system stability.
- **Security**: `AuthFilter` intercepts all requests to ensure only logged-in users can access protected pages.
