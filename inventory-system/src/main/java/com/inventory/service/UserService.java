package com.inventory.service;

import com.inventory.interfaces.Manageable;
import com.inventory.model.User;
import com.inventory.util.FileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements Manageable<User> {

    @Value("${users.file}")
    private String userFile;

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            user.setId(FileHandler.generateId("USR"));
            String line = format(user);
            FileHandler.writeToFile(userFile, line, true);
        } else {
            FileHandler.updateLine(userFile, user.getId(), format(user));
        }
    }

    @Override
    public void delete(String id) {
        FileHandler.deleteLine(userFile, id);
    }

    @Override
    public User findById(String id) {
        List<String> lines = FileHandler.readAllLines(userFile);
        for (String l : lines) {
            if (l.startsWith(id + "|")) {
                return parse(l);
            }
        }
        return null;
    }

    public User findByUsername(String username) {
        return findAll().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        List<String> lines = FileHandler.readAllLines(userFile);
        List<User> users = new ArrayList<>();
        for (String l : lines) {
            users.add(parse(l));
        }
        return users;
    }

    private User parse(String line) {
        String[] parts = line.split("\\|");
        User u = new User();
        u.setId(parts[0]);
        u.setUsername(parts[1]);
        u.setPasswordHash(parts[2]);
        u.setFullName(parts[3]);
        u.setEmail(parts[4]);
        u.setRole(parts[5]);
        // createdAt parsing omitted for brevity
        return u;
    }

    private String format(User u) {
        return String.join("|",
                u.getId(),
                u.getUsername(),
                u.getPasswordHash(),
                u.getFullName(),
                u.getEmail(),
                u.getRole(),
                u.getCreatedAt() != null ? u.getCreatedAt().toString() : ""
        );
    }
}