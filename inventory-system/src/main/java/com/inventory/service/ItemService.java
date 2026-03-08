package com.inventory.service;

import com.inventory.interfaces.Manageable;
import com.inventory.model.Item;
import com.inventory.util.FileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService implements Manageable<Item> {

    @Value("${items.file}")
    private String itemFile;

    @Override
    public void save(Item item) {
        if (item.getId() == null) {
            item.setId(FileHandler.generateId("ITM"));
            String line = format(item);
            FileHandler.writeToFile(itemFile, line, true);
        } else {
            FileHandler.updateLine(itemFile, item.getId(), format(item));
        }
    }

    @Override
    public void delete(String id) {
        FileHandler.deleteLine(itemFile, id);
        // log deletion transaction elsewhere
    }

    @Override
    public Item findById(String id) {
        List<String> lines = FileHandler.readAllLines(itemFile);
        for (String l : lines) {
            if (l.startsWith(id + "|")) {
                return parse(l);
            }
        }
        return null;
    }

    // convenience search methods
    public List<Item> search(String name, String category, String status) {
        return findAll().stream()
                .filter(i -> (name == null || i.getItemName().toLowerCase().contains(name.toLowerCase())))
                .filter(i -> (category == null || i.getCategory().equalsIgnoreCase(category)))
                .filter(i -> (status == null || i.getStatus().equalsIgnoreCase(status)))
                .toList();
    }

    @Override
    public List<Item> findAll() {
        List<String> lines = FileHandler.readAllLines(itemFile);
        List<Item> items = new ArrayList<>();
        for (String l : lines) {
            items.add(parse(l));
        }
        return items;
    }

    private Item parse(String line) {
        String[] parts = line.split("\\|");
        Item item = new Item();
        item.setId(parts[0]);
        item.setItemName(parts[1]);
        item.setCategory(parts[2]);
        item.setQuantity(Integer.parseInt(parts[3]));
        item.setUnitPrice(Double.parseDouble(parts[4]));
        item.setSupplier(parts[5]);
        item.setLastUpdated(null); // parse if needed
        item.setStatus(parts[7]);
        return item;
    }

    private String format(Item item) {
        return String.join("|",
                item.getId(),
                item.getItemName(),
                item.getCategory(),
                String.valueOf(item.getQuantity()),
                String.valueOf(item.getUnitPrice()),
                item.getSupplier(),
                item.getLastUpdated() != null ? item.getLastUpdated().toString() : "",
                item.getStatus()
        );
    }
}