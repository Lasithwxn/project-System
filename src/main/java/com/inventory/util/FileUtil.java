package com.inventory.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for common file operations. Demonstrates **File Handling Implementation**.
 */
public class FileUtil {

    /**
     * Reads all lines from a file.
     * @param filePath The path to the file.
     * @return A list of strings, each representing a line in the file.
     */
    public static List<String> readAllLines(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + " - " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Writes all lines to a file, overwriting existing content.
     * @param filePath The path to the file.
     * @param lines The list of strings to write to the file.
     */
    public static void writeAllLines(String filePath, List<String> lines) {
        Path path = Paths.get(filePath);
        try {
            // Ensure parent directories exist
            Files.createDirectories(path.getParent());
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filePath + " - " + e.getMessage());
        }
    }

    /**
     * Appends a single line to a file.
     * @param filePath The path to the file.
     * @param line The string to append to the file.
     */
    public static void appendLine(String filePath, String line) {
        Path path = Paths.get(filePath);
        try {
            // Ensure parent directories exist
            Files.createDirectories(path.getParent());
            Files.write(path, Collections.singletonList(line), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error appending to file: " + filePath + " - " + e.getMessage());
        }
    }

    /**
     * Deletes a line from a file based on its unique ID.
     * Assumes the ID is the first element in a comma-separated line.
     * @param filePath The path to the file.
     * @param id The unique ID to search for.
     */
    public static void deleteLineById(String filePath, String id) {
        List<String> lines = readAllLines(filePath);
        List<String> filteredLines = lines.stream()
                .filter(line -> !line.startsWith(id + ","))
                .collect(Collectors.toList());
        writeAllLines(filePath, filteredLines);
    }
}
