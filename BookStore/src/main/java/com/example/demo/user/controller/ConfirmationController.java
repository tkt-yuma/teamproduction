package com.example.demo.user.controller;
//1月15日 遠所作業

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.user.service.OrderService;

@Controller
@RequestMapping("/confirmation")
public class ConfirmationController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String showConfirmation(Model model) {
        Integer userId = getCurrentUserId();
        int estimatedDeliveryDays = orderService.getEstimatedDeliveryDays(userId);
        
        model.addAttribute("estimatedDeliveryDays", estimatedDeliveryDays);
        return "confirmation";
    }

    @GetMapping("/top")
    public String goToProductPage() {
        return "redirect:/product"; // productページへのリダイレクト
    }

    private Integer getCurrentUserId() {
        // 認証システムからユーザーIDを取得する実装
        return 1; // 仮の実装
    }
}

