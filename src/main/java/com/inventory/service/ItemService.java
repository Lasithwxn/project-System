package com.inventory.service;

import com.inventory.model.Item;
import com.inventory.model.Product;
import com.inventory.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for item-related operations. Demonstrates **CRUD Operations** and **Polymorphism**.
 */
public class ItemService {
    private static final String ITEMS_FILE = "data/items.txt";

    /**
     * Retrieves all items from the file.
     * @return A list of Item objects.
     */
    public List<Item> getAllItems() {
        List<String> lines = FileUtil.readAllLines(ITEMS_FILE);
        List<Item> items = new ArrayList<>();
        for (String line : lines) {
            try {
                // In this simplified project, all items in items.txt are Products
                items.add(Product.fromCSV(line));
            } catch (Exception e) {
                System.err.println("Skipping invalid item record: " + line + " - " + e.getMessage());
            }
        }
        return items;
    }

    /**
     * Finds an item by its unique ID.
     * @param id The item's ID.
     * @return The Item object if found, null otherwise.
     */
    public Item findItemById(String id) {
        return getAllItems().stream()
                .filter(item -> item.getItemId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a new item to the system.
     * @param item The Item object to add.
     * @return true if successful, false otherwise.
     */
    public boolean addItem(Item item) {
        if (findItemById(item.getItemId()) != null) {
            return false; // Item ID already exists
        }
        FileUtil.appendLine(ITEMS_FILE, item.toCSV());
        return true;
    }

    /**
     * Updates an existing item's information.
     * @param updatedItem The Item object with updated information.
     * @return true if successful, false otherwise.
     */
    public boolean updateItem(Item updatedItem) {
        List<Item> items = getAllItems();
        boolean found = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemId().equals(updatedItem.getItemId())) {
                items.set(i, updatedItem);
                found = true;
                break;
            }
        }
        if (found) {
            new FileService().writeItemsToFile(ITEMS_FILE, items);
            return true;
        }
        return false;
    }

    /**
     * Deletes an item from the system.
     * @param id The ID of the item to delete.
     * @return true if successful, false otherwise.
     */
    public boolean deleteItem(String id) {
        FileUtil.deleteLineById(ITEMS_FILE, id);
        return true;
    }

    /**
     * Overloaded method to search for items by name.
     * Demonstrates **Polymorphism**.
     * @param name The name to search for.
     * @return A list of matching Item objects.
     */
    public List<Item> searchItems(String name) {
        return getAllItems().stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Overloaded method to search for items by name and category.
     * Demonstrates **Polymorphism**.
     * @param name The name to search for.
     * @param category The category to filter by.
     * @return A list of matching Item objects.
     */
    public List<Item> searchItems(String name, String category) {
        return getAllItems().stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()) &&
                                ((Product) item).getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}
