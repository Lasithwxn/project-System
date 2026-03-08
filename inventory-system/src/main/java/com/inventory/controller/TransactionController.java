package com.inventory.controller;

import com.inventory.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService txnService;

    @GetMapping
    public String history(@RequestParam(required = false) String itemId,
                          @RequestParam(required = false) String userId,
                          @RequestParam(required = false) String fromDate,
                          @RequestParam(required = false) String toDate,
                          Model model) {
        var all = txnService.findAll();
        if (itemId != null && !itemId.isBlank()) {
            all = all.stream().filter(t -> t.getItemId().equalsIgnoreCase(itemId)).toList();
        }
        if (userId != null && !userId.isBlank()) {
            all = all.stream().filter(t -> t.getUserId().equalsIgnoreCase(userId)).toList();
        }
        // date filtering omitted for brevity
        model.addAttribute("txns", all);
        model.addAttribute("searchItem", itemId);
        model.addAttribute("searchUser", userId);
        return "transactions/history";
    }
}