package com.inventory.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileHandler {
    public static void writeToFile(String filePath, String data, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readAllLines(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void updateLine(String filePath, String id, String newData) {
        List<String> lines = readAllLines(filePath);
        List<String> updated = lines.stream()
            .map(line -> line.startsWith(id + "|") ? newData : line)
            .collect(Collectors.toList());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String l : updated) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLine(String filePath, String id) {
        List<String> lines = readAllLines(filePath);
        List<String> filtered = lines.stream()
            .filter(line -> !line.startsWith(id + "|"))
            .collect(Collectors.toList());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String l : filtered) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateId(String prefix) {
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return prefix + "-" + uuid;
    }
}