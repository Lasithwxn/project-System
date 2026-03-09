package com.inventory.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a user in the Inventory Management System.
 * Demonstrates **Encapsulation** by keeping fields private and providing public getters/setters.
 */
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private Status status;
    private LocalDate dateRegistered;

    public enum Role {
        ADMIN, MANAGER, STAFF
    }

    public enum Status {
        ACTIVE, INACTIVE
    }

    public User(String id, String username, String password, String email, Role role, Status status, LocalDate dateRegistered) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
        this.dateRegistered = dateRegistered;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    // Setters (with basic validation as per encapsulation principle)
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty.");
        }
        this.id = id;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        // In a real application, this would involve hashing the password
        this.password = password;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public void setRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }
        this.role = role;
    }

    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        this.status = status;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        if (dateRegistered == null) {
            throw new IllegalArgumentException("Registration date cannot be null.");
        }
        this.dateRegistered = dateRegistered;
    }

    /**
     * Static factory method to create a User object from a CSV string.
     * @param csvLine A comma-separated string representing user data.
     * @return A User object.
     * @throws IllegalArgumentException if the CSV format is invalid.
     */
    public static User fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid CSV format for User: " + csvLine);
        }
        try {
            String id = parts[0];
            String username = parts[1];
            String password = parts[2]; // In a real app, this would be hashed
            String email = parts[3];
            Role role = Role.valueOf(parts[4].toUpperCase());
            Status status = Status.valueOf(parts[5].toUpperCase());
            LocalDate dateRegistered = LocalDate.parse(parts[6]);
            return new User(id, username, password, email, role, status, dateRegistered);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            throw new IllegalArgumentException("Error parsing User CSV line: " + csvLine, e);
        }
    }

    /**
     * Converts the User object to a CSV string for file storage.
     * @return A comma-separated string of user data.
     */
    public String toCSV() {
        return String.join(",",
                this.id,
                this.username,
                this.password, // In a real app, this would be hashed
                this.email,
                this.role.name(),
                this.status.name(),
                this.dateRegistered.toString()
        );
    }

    @Override
    public String toString() {
        return "User{" +
               "id='" + id + '\'' +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", role=" + role +
               ", status=" + status +
               ", dateRegistered=" + dateRegistered +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
