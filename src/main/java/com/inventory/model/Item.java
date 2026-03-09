package com.inventory.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Abstract base class for inventory items. Demonstrates **Inheritance** and **Abstraction**.
 * Provides common properties and methods for all types of items.
 */
public abstract class Item {
    private String itemId;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private LocalDate lastUpdated;

    public Item(String itemId, String name, String description, double price, int quantity, LocalDate lastUpdated) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
    }

    // Getters
    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    // Setters (with basic validation)
    public void setItemId(String itemId) {
        if (itemId == null || itemId.trim().isEmpty()) {
            throw new IllegalArgumentException("Item ID cannot be empty.");
        }
        this.itemId = itemId;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        if (lastUpdated == null) {
            throw new IllegalArgumentException("Last updated date cannot be null.");
        }
        this.lastUpdated = lastUpdated;
    }

    /**
     * Abstract method to convert the Item object to a CSV string.
     * This forces subclasses to implement their specific CSV format.
     * @return A comma-separated string of item data.
     */
    public abstract String toCSV();

    /**
     * Static factory method to create an Item object from a CSV string.
     * This method will delegate to specific subclass factory methods based on item type if needed,
     * or can be overridden in subclasses for simpler cases.
     * For this project, we'll handle the distinction in the service layer or specific factory methods.
     * @param csvLine A comma-separated string representing item data.
     * @return An Item object.
     * @throws IllegalArgumentException if the CSV format is invalid.
     */
    public static Item fromCSV(String csvLine) {
        // This method will be implemented in concrete subclasses or handled by a factory in the service layer
        // For now, it's a placeholder to satisfy the requirement of Item.fromCSV()
        throw new UnsupportedOperationException("fromCSV() must be implemented by concrete Item subclasses or a dedicated factory.");
    }

    @Override
    public String toString() {
        return "Item{" +
               "itemId=\'" + itemId + '\'' +
               ", name=\'" + name + '\'' +
               ", description=\'" + description + '\'' +
               ", price=" + price +
               ", quantity=" + quantity +
               ", lastUpdated=" + lastUpdated +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemId, item.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
}
