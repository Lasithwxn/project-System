package com.inventory.service;

import com.inventory.interfaces.Manageable;
import com.inventory.model.Transaction;
import com.inventory.util.FileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService implements Manageable<Transaction> {

    @Value("${transactions.file}")
    private String txnFile;

    @Override
    public void save(Transaction txn) {
        if (txn.getId() == null) {
            txn.setId(FileHandler.generateId("TXN"));
            String line = format(txn);
            FileHandler.writeToFile(txnFile, line, true);
        } else {
            // transactions are immutable; ignore
        }
    }

    @Override
    public void delete(String id) {
        // not allowed for audit trail
    }

    @Override
    public Transaction findById(String id) {
        List<String> lines = FileHandler.readAllLines(txnFile);
        for (String l : lines) {
            if (l.startsWith(id + "|")) {
                return parse(l);
            }
        }
        return null;
    }

    @Override
    public List<Transaction> findAll() {
        List<String> lines = FileHandler.readAllLines(txnFile);
        List<Transaction> txns = new ArrayList<>();
        for (String l : lines) {
            txns.add(parse(l));
        }
        return txns;
    }

    private Transaction parse(String line) {
        String[] parts = line.split("\\|");
        Transaction t = new Transaction();
        t.setId(parts[0]);
        t.setItemId(parts[1]);
        t.setUserId(parts[2]);
        t.setType(parts[3]);
        t.setQuantity(Integer.parseInt(parts[4]));
        // timestamp and notes parsing omitted
        t.setNotes(parts[6]);
        return t;
    }

    private String format(Transaction t) {
        return String.join("|",
                t.getId(),
                t.getItemId(),
                t.getUserId(),
                t.getType(),
                String.valueOf(t.getQuantity()),
                t.getTimestamp() != null ? t.getTimestamp().toString() : "",
                t.getNotes()
        );
    }
}