<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.inventory.model.User, com.inventory.model.Item, com.inventory.model.Product" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
    Item item = (Item) request.getAttribute("item");
    Product product = (item != null) ? (Product) item : null;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InvTrack - Edit Item</title>
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
                        <span class="navbar-brand">Edit Item</span>
                    </div>
                </nav>

                <!-- Form Content -->
                <div class="container-fluid p-4">
                    <div class="row">
                        <div class="col-md-8">
                            <div class="form-container">
                                <% if (product != null) { %>
                                    <div class="info-box">
                                        <strong>Item ID:</strong> <%= item.getItemId() %><br>
                                        <strong>Last Updated:</strong> <%= item.getLastUpdated() %>
                                    </div>

                                    <form method="POST" action="${pageContext.request.contextPath}/items" onsubmit="return validateForm()">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="id" value="<%= item.getItemId() %>">
                                        
                                        <div class="mb-3">
                                            <label for="name" class="form-label">Item Name *</label>
                                            <input type="text" class="form-control" id="name" name="name" value="<%= item.getName() %>" required>
                                        </div>

                                        <div class="mb-3">
                                            <label for="description" class="form-label">Description</label>
                                            <textarea class="form-control" id="description" name="description" rows="3"><%= item.getDescription() %></textarea>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label for="price" class="form-label">Price *</label>
                                                <input type="number" class="form-control" id="price" name="price" step="0.01" value="<%= item.getPrice() %>" required>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="quantity" class="form-label">Quantity *</label>
                                                <input type="number" class="form-control" id="quantity" name="quantity" value="<%= item.getQuantity() %>" required>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label for="category" class="form-label">Category *</label>
                                                <select class="form-select" id="category" name="category" required>
                                                    <option value="">Select Category</option>
                                                    <option value="Electronics" <%= "Electronics".equals(product.getCategory()) ? "selected" : "" %>>Electronics</option>
                                                    <option value="Furniture" <%= "Furniture".equals(product.getCategory()) ? "selected" : "" %>>Furniture</option>
                                                    <option value="Stationery" <%= "Stationery".equals(product.getCategory()) ? "selected" : "" %>>Stationery</option>
                                                    <option value="Other" <%= "Other".equals(product.getCategory()) ? "selected" : "" %>>Other</option>
                                                </select>
                                            </div>
                                            <div class="col-md-6 mb-3">
                                                <label for="supplier" class="form-label">Supplier *</label>
                                                <input type="text" class="form-control" id="supplier" name="supplier" value="<%= product.getSupplier() %>" required>
                                            </div>
                                        </div>

                                        <div class="d-flex gap-2">
                                            <button type="submit" class="btn btn-primary">
                                                <i class="bi bi-check-circle"></i> Update Item
                                            </button>
                                            <a href="${pageContext.request.contextPath}/items?action=list" class="btn btn-secondary">
                                                <i class="bi bi-x-circle"></i> Cancel
                                            </a>
                                        </div>
                                    </form>
                                <% } else { %>
                                    <div class="alert alert-danger">Item not found.</div>
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
            let name = document.getElementById('name').value.trim();
            let price = document.getElementById('price').value.trim();
            let quantity = document.getElementById('quantity').value.trim();
            let category = document.getElementById('category').value;
            let supplier = document.getElementById('supplier').value.trim();

            if (name === '' || price === '' || quantity === '' || category === '' || supplier === '') {
                alert('All required fields must be filled.');
                return false;
            }
            if (isNaN(price) || price <= 0) {
                alert('Price must be a valid positive number.');
                return false;
            }
            if (isNaN(quantity) || quantity < 0) {
                alert('Quantity must be a valid non-negative number.');
                return false;
            }
            return true;
        }
    </script>
</body>
</html>
