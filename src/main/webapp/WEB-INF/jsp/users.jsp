<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.inventory.model.User" %>
<%@ page import="java.util.List" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null || loggedInUser.getRole() != User.Role.ADMIN) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
    List<User> users = (List<User>) request.getAttribute("users");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InvTrack - User Management</title>
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
        .sidebar a.active {
            background: rgba(255, 255, 255, 0.3);
        }
        .navbar {
            background: white;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }
        .table-container {
            background: white;
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
        }
        .badge-role {
            font-size: 0.85rem;
            padding: 0.5rem 0.75rem;
        }
        .badge-status {
            font-size: 0.85rem;
            padding: 0.5rem 0.75rem;
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
                <a href="${pageContext.request.contextPath}/users?action=list" class="active">
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
                        <span class="navbar-brand">User Management</span>
                        <div class="ms-auto d-flex align-items-center">
                            <span class="me-3">
                                <strong><%= loggedInUser.getUsername() %></strong>
                                <span class="badge bg-primary badge-role">ADMIN</span>
                            </span>
                        </div>
                    </div>
                </nav>

                <!-- User Management Content -->
                <div class="container-fluid p-4">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h2>Users</h2>
                        <a href="${pageContext.request.contextPath}/jsp/addUser.jsp" class="btn btn-primary">
                            <i class="bi bi-plus-circle"></i> Add New User
                        </a>
                    </div>

                    <div class="table-container">
                        <div class="mb-3">
                            <input type="text" id="searchInput" class="form-control" placeholder="Search by username or role...">
                        </div>
                        <div class="table-responsive">
                            <table class="table table-hover" id="usersTable">
                                <thead class="table-light">
                                    <tr>
                                        <th>ID</th>
                                        <th>Username</th>
                                        <th>Email</th>
                                        <th>Role</th>
                                        <th>Status</th>
                                        <th>Registered</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% if (users != null && !users.isEmpty()) {
                                        for (User user : users) { %>
                                            <tr>
                                                <td><%= user.getId() %></td>
                                                <td><%= user.getUsername() %></td>
                                                <td><%= user.getEmail() %></td>
                                                <td>
                                                    <% if (user.getRole() == User.Role.ADMIN) { %>
                                                        <span class="badge bg-primary badge-role">ADMIN</span>
                                                    <% } else if (user.getRole() == User.Role.MANAGER) { %>
                                                        <span class="badge bg-success badge-role">MANAGER</span>
                                                    <% } else { %>
                                                        <span class="badge bg-secondary badge-role">STAFF</span>
                                                    <% } %>
                                                </td>
                                                <td>
                                                    <% if (user.getStatus() == User.Status.ACTIVE) { %>
                                                        <span class="badge bg-success badge-status">ACTIVE</span>
                                                    <% } else { %>
                                                        <span class="badge bg-danger badge-status">INACTIVE</span>
                                                    <% } %>
                                                </td>
                                                <td><%= user.getDateRegistered() %></td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/users?action=edit&id=<%= user.getId() %>" class="btn btn-sm btn-info">
                                                        <i class="bi bi-pencil"></i> Edit
                                                    </a>
                                                    <form method="POST" action="${pageContext.request.contextPath}/users" style="display:inline;">
                                                        <input type="hidden" name="action" value="toggleStatus">
                                                        <input type="hidden" name="id" value="<%= user.getId() %>">
                                                        <button type="submit" class="btn btn-sm btn-warning">
                                                            <i class="bi bi-toggle-on"></i> Toggle
                                                        </button>
                                                    </form>
                                                    <form method="POST" action="${pageContext.request.contextPath}/users" style="display:inline;" onsubmit="return confirm('Are you sure?');">
                                                        <input type="hidden" name="action" value="delete">
                                                        <input type="hidden" name="id" value="<%= user.getId() %>">
                                                        <button type="submit" class="btn btn-sm btn-danger">
                                                            <i class="bi bi-trash"></i> Delete
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                    <% }
                                    } else { %>
                                        <tr>
                                            <td colspan="7" class="text-center">No users found.</td>
                                        </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('searchInput').addEventListener('keyup', function() {
            let searchTerm = this.value.toLowerCase();
            let tableRows = document.querySelectorAll('#usersTable tbody tr');
            tableRows.forEach(row => {
                let username = row.cells[1].textContent.toLowerCase();
                let role = row.cells[3].textContent.toLowerCase();
                if (username.includes(searchTerm) || role.includes(searchTerm)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
    </script>
</body>
</html>
