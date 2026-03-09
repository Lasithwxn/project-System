package com.inventory.servlet;

import com.inventory.model.Item;
import com.inventory.model.Product;
import com.inventory.service.ItemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Servlet for managing inventory item CRUD operations.
 */
@WebServlet("/items")
public class ItemServlet extends HttpServlet {
    private final ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("list".equals(action)) {
            List<Item> items = itemService.getAllItems();
            request.setAttribute("items", items);
            request.getRequestDispatcher("/jsp/items.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            String id = request.getParameter("id");
            Item item = itemService.findItemById(id);
            request.setAttribute("item", item);
            request.getRequestDispatcher("/jsp/editItem.jsp").forward(request, response);
        } else if ("search".equals(action)) {
            String query = request.getParameter("query");
            String category = request.getParameter("category");
            List<Item> results;
            if (category != null && !category.trim().isEmpty()) {
                results = itemService.searchItems(query, category);
            } else {
                results = itemService.searchItems(query);
            }
            request.setAttribute("items", results);
            request.getRequestDispatcher("/jsp/items.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/items?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String category = request.getParameter("category");
            String supplier = request.getParameter("supplier");
            String id = "INV" + System.currentTimeMillis();

            Product newProduct = new Product(id, name, description, price, quantity, LocalDate.now(), category, supplier);
            itemService.addItem(newProduct);
            response.sendRedirect(request.getContextPath() + "/items?action=list");
        } else if ("update".equals(action)) {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String category = request.getParameter("category");
            String supplier = request.getParameter("supplier");

            Product updatedProduct = new Product(id, name, description, price, quantity, LocalDate.now(), category, supplier);
            itemService.updateItem(updatedProduct);
            response.sendRedirect(request.getContextPath() + "/items?action=list");
        } else if ("delete".equals(action)) {
            String id = request.getParameter("id");
            itemService.deleteItem(id);
            response.sendRedirect(request.getContextPath() + "/items?action=list");
        }
    }
}
