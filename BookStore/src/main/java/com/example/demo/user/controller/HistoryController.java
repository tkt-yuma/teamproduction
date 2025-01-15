package com.example.demo.user.controller;
//1月15日 遠所作業

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.user.entity.SellManagement;
import com.example.demo.user.service.OrderService;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String showPurchaseHistory(Model model) {
        Integer userId = getCurrentUserId();
        List<SellManagement> purchaseHistory = orderService.getPurchaseHistoryForUser(userId);
        
        model.addAttribute("purchaseHistory", purchaseHistory);
        return "history"; // history.htmlを返す
    }

    private Integer getCurrentUserId() {
        // 認証システムからユーザーIDを取得する実装
        return 1; // 仮の実装
    }
}
