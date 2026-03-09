<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.inventory.model.User" %>
<%@ page import="com.inventory.service.ItemService" %>
<%@ page import="com.inventory.service.UserService" %>
<%@ page import="java.util.List" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
    ItemService itemService = new ItemService();
    UserService userService = new UserService();
    int totalItems = itemService.getAllItems().size();
    long activeUsers = userService.getAllUsers().stream()
        .filter(u -> u.getStatus() == User.Status.ACTIVE)
        .count();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InvTrack - Dashboard</title>
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
        .stat-card {
            background: white;
            border-radius: 10px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
            text-align: center;
        }
        .stat-card h3 {
            color: #667eea;
            font-weight: bold;
            font-size: 2rem;
        }
        .stat-card p {
            color: #6c757d;
            margin: 0;
        }
        .badge-role {
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
                <a href="${pageContext.request.contextPath}/jsp/index.jsp" class="active">
                    <i class="bi bi-speedometer2"></i> Dashboard
                </a>
                <a href="${pageContext.request.contextPath}/items?action=list">
                    <i class="bi bi-box"></i> Items
                </a>
                <% if (loggedInUser.getRole() == User.Role.ADMIN) { %>
                    <a href="${pageContext.request.contextPath}/users?action=list">
                        <i class="bi bi-people"></i> User Management
                    </a>
                <% } %>
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
                        <span class="navbar-brand">Dashboard</span>
                        <div class="ms-auto d-flex align-items-center">
                            <span class="me-3">
                                <strong><%= loggedInUser.getUsername() %></strong>
                                <% if (loggedInUser.getRole() == User.Role.ADMIN) { %>
                                    <span class="badge bg-primary badge-role">ADMIN</span>
                                <% } else if (loggedInUser.getRole() == User.Role.MANAGER) { %>
                                    <span class="badge bg-success badge-role">MANAGER</span>
                                <% } else { %>
                                    <span class="badge bg-secondary badge-role">STAFF</span>
                                <% } %>
                            </span>
                        </div>
                    </div>
                </nav>

                <!-- Dashboard Content -->
                <div class="container-fluid p-4">
                    <h2 class="mb-4">Welcome, <%= loggedInUser.getUsername() %>!</h2>
                    
                    <div class="row">
                        <div class="col-md-6 col-lg-3">
                            <div class="stat-card">
                                <h3><%= totalItems %></h3>
                                <p>Total Items</p>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-3">
                            <div class="stat-card">
                                <h3><%= activeUsers %></h3>
                                <p>Active Users</p>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-3">
                            <div class="stat-card">
                                <h3>$0</h3>
                                <p>Total Value</p>
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-3">
                            <div class="stat-card">
                                <h3>0</h3>
                                <p>Low Stock Items</p>
                            </div>
                        </div>
                    </div>

                    <div class="row mt-4">
                        <div class="col-md-12">
                            <div class="stat-card">
                                <h5>Quick Actions</h5>
                                <a href="${pageContext.request.contextPath}/jsp/addItem.jsp" class="btn btn-primary btn-sm me-2">
                                    <i class="bi bi-plus-circle"></i> Add New Item
                                </a>
                                <% if (loggedInUser.getRole() == User.Role.ADMIN) { %>
                                    <a href="${pageContext.request.contextPath}/jsp/addUser.jsp" class="btn btn-success btn-sm">
                                        <i class="bi bi-person-plus"></i> Add New User
                                    </a>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
