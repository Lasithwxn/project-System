package com.inventory.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a stock alert in the inventory system, inheriting from the abstract Item class.
 * Demonstrates **Inheritance**.
 * Note: Price and quantity from the base Item class are not directly applicable to a StockAlert
 * but are included to satisfy the inheritance requirement. They will be set to default values.
 */
public class StockAlert extends Item {
    private String referencedItemId; // The ID of the item this alert refers to
    private String alertType;      // e.g., "LowStock", "Expired"

    public StockAlert(String alertId, String referencedItemId, String alertType, String message, LocalDate alertDate) {
        // Mapping StockAlert properties to Item properties to satisfy inheritance
        // price and quantity are not relevant for an alert, so setting to 0
        super(alertId, alertType, message, 0.0, 0, alertDate);
        this.referencedItemId = referencedItemId;
        this.alertType = alertType;
    }

    // Getters specific to StockAlert
    public String getReferencedItemId() {
        return referencedItemId;
    }

    public String getAlertType() {
        return alertType;
    }

    // Setters specific to StockAlert
    public void setReferencedItemId(String referencedItemId) {
        if (referencedItemId == null || referencedItemId.trim().isEmpty()) {
            throw new IllegalArgumentException("Referenced Item ID cannot be empty.");
        }
        this.referencedItemId = referencedItemId;
    }

    public void setAlertType(String alertType) {
        if (alertType == null || alertType.trim().isEmpty()) {
            throw new IllegalArgumentException("Alert type cannot be empty.");
        }
        this.alertType = alertType;
    }

    /**
     * Converts the StockAlert object to a CSV string for file storage.
     * Implements the abstract toCSV() method from the Item class.
     * @return A comma-separated string of stock alert data.
     */
    @Override
    public String toCSV() {
        return String.join(",",
                getItemId(), // alertId
                getReferencedItemId(),
                getAlertType(),
                getDescription(), // message
                getLastUpdated().toString() // alertDate
        );
    }

    /**
     * Static factory method to create a StockAlert object from a CSV string.
     * @param csvLine A comma-separated string representing stock alert data.
     * @return A StockAlert object.
     * @throws IllegalArgumentException if the CSV format is invalid.
     */
    public static StockAlert fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid CSV format for StockAlert: " + csvLine);
        }
        try {
            String alertId = parts[0];
            String referencedItemId = parts[1];
            String alertType = parts[2];
            String message = parts[3];
            LocalDate alertDate = LocalDate.parse(parts[4]);
            return new StockAlert(alertId, referencedItemId, alertType, message, alertDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error parsing StockAlert CSV line: " + csvLine, e);
        }
    }

    @Override
    public String toString() {
        return "StockAlert{" +
               "alertId='" + getItemId() + "'" +
               ", referencedItemId='" + referencedItemId + "'" +
               ", alertType='" + alertType + "'" +
               ", message='" + getDescription() + "'" +
               ", alertDate=" + getLastUpdated() +
               "}";
    }
}
