package com.inventory.controller;

import com.inventory.service.ItemService;
import com.inventory.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private TransactionService txnService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalItems", itemService.findAll().size());
        model.addAttribute("recentTxns", txnService.findAll());
        return "dashboard";
    }
}