<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.inventory.model.User" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null || loggedInUser.getRole() != User.Role.ADMIN) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
    User user = (User) request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InvTrack - Edit User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .sidebar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            color: white;
            padding: 2rem 0;
        }
        .sidebar a {
            color: white;
            text-decoration: none;
            display: block;
            padding: 0.75rem 1.5rem;
            margin: 0.5rem 0;
            border-radius: 5px;
            transition: background 0.3s;
        }
        .sidebar a:hover {
            background: rgba(255, 255, 255, 0.2);
        }
        .navbar {
            background: white;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }
        .form-container {
            background: white;
            border-radius: 10px;
            padding: 2rem;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }
        .info-box {
            background: #f8f9fa;
            border-left: 4px solid #667eea;
            padding: 1rem;
            margin-bottom: 1rem;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-2 sidebar">
                <h4 class="mb-4 ps-3">InvTrack</h4>
                <a href="${pageContext.request.contextPath}/jsp/index.jsp">
                    <i class="bi bi-speedometer2"></i> Dashboard
                </a>
                <a href="${pageContext.request.contextPath}/items?action=list">
                    <i class="bi bi-box"></i> Items
                </a>
                <a href="${pageContext.request.contextPath}/users?action=list">
                    <i class="bi bi-people"></i> User Management
                </a>
                <hr class="bg-white">
                <a href="${pageContext.request.contextPath}/auth?action=logout">
                    <i class="bi bi-box-arrow-right"></i> Logout
                </a>
            </div>

            <!-- Main Content -->
            <div class="col-md-10">
                <!-- Navbar -->
                <nav class="navbar navbar-expand-lg navbar-light">
                    <div class="container-fluid">
                        <span class="navbar-brand">Edit User</span>
                    </div>
                </nav>

                <!-- Form Content -->
                <div class="container-fluid p-4">
                    <div class="row">
                        <div class="col-md-8">
                            <div class="form-container">
                                <% if (user != null) { %>
                                    <div class="info-box">
                                        <strong>User ID:</strong> <%= user.getId() %><br>
                                        <strong>Registered:</strong> <%= user.getDateRegistered() %>
                                    </div>

                                    <form method="POST" action="${pageContext.request.contextPath}/users" onsubmit="return validateForm()">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="id" value="<%= user.getId() %>">
                                        
                                        <div class="mb-3">
                                            <label for="username" class="form-label">Username *</label>
                                            <input type="text" class="form-control" id="username" name="username" value="<%= user.getUsername() %>" required>
                                        </div>

                                        <div class="mb-3">
                                            <label for="email" class="form-label">Email *</label>
                                            <input type="email" class="form-control" id="email" name="email" value="<%= user.getEmail() %>" required>
                                        </div>

                                        <div class="mb-3">
                                            <label for="password" class="form-label">Password (leave blank to keep existing)</label>
                                            <input type="password" class="form-control" id="password" name="password">
                                        </div>

                                        <div class="mb-3">
                                            <label for="role" class="form-label">Role *</label>
                                            <select class="form-select" id="role" name="role" required>
                                                <option value="ADMIN" <%= user.getRole() == User.Role.ADMIN ? "selected" : "" %>>Admin</option>
                                                <option value="MANAGER" <%= user.getRole() == User.Role.MANAGER ? "selected" : "" %>>Manager</option>
                                                <option value="STAFF" <%= user.getRole() == User.Role.STAFF ? "selected" : "" %>>Staff</option>
                                            </select>
                                        </div>

                                        <div class="mb-3">
                                            <label for="status" class="form-label">Status</label>
                                            <select class="form-select" id="status" name="status">
                                                <option value="ACTIVE" <%= user.getStatus() == User.Status.ACTIVE ? "selected" : "" %>>Active</option>
                                                <option value="INACTIVE" <%= user.getStatus() == User.Status.INACTIVE ? "selected" : "" %>>Inactive</option>
                                            </select>
                                        </div>

                                        <div class="d-flex gap-2">
                                            <button type="submit" class="btn btn-primary">
                                                <i class="bi bi-check-circle"></i> Update User
                                            </button>
                                            <a href="${pageContext.request.contextPath}/users?action=list" class="btn btn-secondary">
                                                <i class="bi bi-x-circle"></i> Cancel
                                            </a>
                                        </div>
                                    </form>
                                <% } else { %>
                                    <div class="alert alert-danger">User not found.</div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function validateForm() {
            let username = document.getElementById('username').value.trim();
            let email = document.getElementById('email').value.trim();
            let role = document.getElementById('role').value;

            if (username === '' || email === '' || role === '') {
                alert('All required fields must be filled.');
                return false;
            }
            return true;
        }
    </script>
</body>
</html>
