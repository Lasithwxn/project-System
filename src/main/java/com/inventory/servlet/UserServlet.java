package com.inventory.servlet;

import com.inventory.model.User;
import com.inventory.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Servlet for managing user-related CRUD operations.
 */
@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("list".equals(action)) {
            List<User> users = userService.getAllUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/jsp/users.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            String id = request.getParameter("id");
            User user = userService.findUserById(id);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/jsp/editUser.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/users?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            User.Role role = User.Role.valueOf(request.getParameter("role").toUpperCase());
            User.Status status = User.Status.valueOf(request.getParameter("status").toUpperCase());
            String id = "USR" + System.currentTimeMillis(); // Simple ID generation

            User newUser = new User(id, username, password, email, role, status, LocalDate.now());
            if (userService.addUser(newUser)) {
                response.sendRedirect(request.getContextPath() + "/users?action=list");
            } else {
                request.setAttribute("error", "Failed to add user. Username may already exist.");
                request.getRequestDispatcher("/jsp/addUser.jsp").forward(request, response);
            }
        } else if ("update".equals(action)) {
            String id = request.getParameter("id");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            User.Role role = User.Role.valueOf(request.getParameter("role").toUpperCase());
            User.Status status = User.Status.valueOf(request.getParameter("status").toUpperCase());

            User existingUser = userService.findUserById(id);
            if (existingUser != null) {
                // Keep existing password if not provided
                if (password == null || password.trim().isEmpty()) {
                    password = existingUser.getPassword();
                }
                User updatedUser = new User(id, username, password, email, role, status, existingUser.getDateRegistered());
                userService.updateUser(updatedUser);
            }
            response.sendRedirect(request.getContextPath() + "/users?action=list");
        } else if ("delete".equals(action)) {
            String id = request.getParameter("id");
            userService.deleteUser(id);
            response.sendRedirect(request.getContextPath() + "/users?action=list");
        } else if ("toggleStatus".equals(action)) {
            String id = request.getParameter("id");
            userService.toggleStatus(id);
            response.sendRedirect(request.getContextPath() + "/users?action=list");
        }
    }
}
