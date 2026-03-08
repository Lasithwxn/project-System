package com.inventory.controller;

import com.inventory.model.Item;
import com.inventory.service.ItemService;
import com.inventory.service.TransactionService;
import com.inventory.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private TransactionService txnService;

    @GetMapping
    public String list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String category,
                       @RequestParam(required = false) String status,
                       Model model) {
        model.addAttribute("items", itemService.search(name, category, status));
        model.addAttribute("searchName", name);
        model.addAttribute("searchCategory", category);
        model.addAttribute("searchStatus", status);
        return "inventory/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("item", new Item());
        return "inventory/form";
    }

    @PostMapping
    public String save(@ModelAttribute Item item) {
        itemService.save(item);
        return "redirect:/items";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "inventory/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Item item) {
        itemService.save(item);
        return "redirect:/items";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        itemService.delete(id);
        // log transaction
        Transaction txn = new Transaction();
        txn.setItemId(id);
        txn.setType("SALE");
        txn.setQuantity(0);
        txn.setTimestamp(LocalDateTime.now());
        txn.setNotes("Item deleted");
        txnService.save(txn);
        return "redirect:/items";
    }
}