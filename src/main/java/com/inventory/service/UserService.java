package com.inventory.service;

import com.inventory.model.User;
import com.inventory.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for user-related operations. Demonstrates **CRUD Operations** and **Polymorphism**.
 */
public class UserService {
    private static final String USERS_FILE = "data/users.txt";

    /**
     * Retrieves all users from the file.
     * @return A list of User objects.
     */
    public List<User> getAllUsers() {
        List<String> lines = FileUtil.readAllLines(USERS_FILE);
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            try {
                users.add(User.fromCSV(line));
            } catch (Exception e) {
                System.err.println("Skipping invalid user record: " + line + " - " + e.getMessage());
            }
        }
        return users;
    }

    /**
     * Finds a user by their unique ID.
     * @param id The user's ID.
     * @return The User object if found, null otherwise.
     */
    public User findUserById(String id) {
        return getAllUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds a user by their username.
     * @param username The username to search for.
     * @return The User object if found, null otherwise.
     */
    public User findUserByUsername(String username) {
        return getAllUsers().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a new user to the system.
     * @param user The User object to add.
     * @return true if successful, false otherwise.
     */
    public boolean addUser(User user) {
        if (findUserByUsername(user.getUsername()) != null) {
            return false; // Username already exists
        }
        FileUtil.appendLine(USERS_FILE, user.toCSV());
        return true;
    }

    /**
     * Updates an existing user's information.
     * @param updatedUser The User object with updated information.
     * @return true if successful, false otherwise.
     */
    public boolean updateUser(User updatedUser) {
        List<User> users = getAllUsers();
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                found = true;
                break;
            }
        }
        if (found) {
            new FileService().writeUsersToFile(USERS_FILE, users);
            return true;
        }
        return false;
    }

    /**
     * Deletes a user from the system.
     * @param id The ID of the user to delete.
     * @return true if successful, false otherwise.
     */
    public boolean deleteUser(String id) {
        FileUtil.deleteLineById(USERS_FILE, id);
        return true;
    }

    /**
     * Toggles the status of a user (ACTIVE/INACTIVE).
     * @param id The ID of the user.
     * @return true if successful, false otherwise.
     */
    public boolean toggleStatus(String id) {
        User user = findUserById(id);
        if (user != null) {
            user.setStatus(user.getStatus() == User.Status.ACTIVE ? User.Status.INACTIVE : User.Status.ACTIVE);
            return updateUser(user);
        }
        return false;
    }

    /**
     * Overloaded method to search for users by username.
     * Demonstrates **Polymorphism**.
     * @param username The username to search for.
     * @return A list of matching User objects.
     */
    public List<User> searchUsers(String username) {
        return getAllUsers().stream()
                .filter(user -> user.getUsername().toLowerCase().contains(username.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Overloaded method to search for users by username and role.
     * Demonstrates **Polymorphism**.
     * @param username The username to search for.
     * @param role The role to filter by.
     * @return A list of matching User objects.
     */
    public List<User> searchUsers(String username, String role) {
        return getAllUsers().stream()
                .filter(user -> user.getUsername().toLowerCase().contains(username.toLowerCase()) &&
                                user.getRole().name().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    /**
     * Validates user credentials for login.
     * @param username The username.
     * @param password The password.
     * @return The User object if valid, null otherwise.
     */
    public User validateCredentials(String username, String password) {
        User user = findUserByUsername(username);
        if (user != null && user.getPassword().equals(password) && user.getStatus() == User.Status.ACTIVE) {
            return user;
        }
        return null;
    }
}
