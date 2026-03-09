package com.inventory.service;

import com.inventory.model.Item;
import com.inventory.model.User;
import com.inventory.util.FileUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for file operations. 
 * Note: Method overloading with List parameters can cause "name clash" errors 
 * due to Type Erasure in Java. Using unique names to avoid this.
 */
public class FileService {

    /**
     * Writes a list of User objects to a file.
     * @param filePath The path to the users file.
     * @param users The list of User objects to write.
     */
    public void writeUsersToFile(String filePath, List<User> users) {
        List<String> lines = users.stream()
                .map(User::toCSV)
                .collect(Collectors.toList());
        FileUtil.writeAllLines(filePath, lines);
    }

    /**
     * Writes a list of Item objects to a file.
     * @param filePath The path to the items file.
     * @param items The list of Item objects to write.
     */
    public void writeItemsToFile(String filePath, List<Item> items) {
        List<String> lines = items.stream()
                .map(Item::toCSV)
                .collect(Collectors.toList());
        FileUtil.writeAllLines(filePath, lines);
    }
}
