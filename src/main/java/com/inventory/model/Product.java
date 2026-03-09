package com.inventory.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a concrete product in the inventory, inheriting from the abstract Item class.
 * Demonstrates **Inheritance**.
 */
public class Product extends Item {
    private String category;
    private String supplier;

    public Product(String itemId, String name, String description, double price, int quantity, LocalDate lastUpdated, String category, String supplier) {
        super(itemId, name, description, price, quantity, lastUpdated);
        this.category = category;
        this.supplier = supplier;
    }

    // Getters
    public String getCategory() {
        return category;
    }

    public String getSupplier() {
        return supplier;
    }

    // Setters
    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty.");
        }
        this.category = category;
    }

    public void setSupplier(String supplier) {
        if (supplier == null || supplier.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier cannot be empty.");
        }
        this.supplier = supplier;
    }

    /**
     * Converts the Product object to a CSV string for file storage.
     * Implements the abstract toCSV() method from the Item class.
     * @return A comma-separated string of product data.
     */
    @Override
    public String toCSV() {
        return String.join(",",
                getItemId(),
                getName(),
                getDescription(),
                String.valueOf(getPrice()),
                String.valueOf(getQuantity()),
                getLastUpdated().toString(),
                this.category,
                this.supplier
        );
    }

    /**
     * Static factory method to create a Product object from a CSV string.
     * @param csvLine A comma-separated string representing product data.
     * @return A Product object.
     * @throws IllegalArgumentException if the CSV format is invalid.
     */
    public static Product fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid CSV format for Product: " + csvLine);
        }
        try {
            String itemId = parts[0];
            String name = parts[1];
            String description = parts[2];
            double price = Double.parseDouble(parts[3]);
            int quantity = Integer.parseInt(parts[4]);
            LocalDate lastUpdated = LocalDate.parse(parts[5]);
            String category = parts[6];
            String supplier = parts[7];
            return new Product(itemId, name, description, price, quantity, lastUpdated, category, supplier);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new IllegalArgumentException("Error parsing Product CSV line: " + csvLine, e);
        }
    }

    @Override
    public String toString() {
        return "Product{" +
               "itemId=\'" + getItemId() + "\', " +
               "name=\'" + getName() + "\', " +
               "description=\'" + getDescription() + "\', " +
               "price=" + getPrice() +
               ", quantity=" + getQuantity() +
               ", lastUpdated=" + getLastUpdated() +
               ", category=\'" + category + "\', " +
               "supplier=\'" + supplier + "\'" +
               "}";
    }
}
