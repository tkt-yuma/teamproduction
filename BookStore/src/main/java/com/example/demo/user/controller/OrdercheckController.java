package com.example.demo.user.controller;
//1月15日 遠所作業(entityのUserinfoと繋げています)

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.entity.CartInfo;
import com.example.demo.user.entity.UserInfo;
import com.example.demo.user.service.OrderService;
import com.example.demo.user.service.UserService;

@Controller
@RequestMapping("/ordercheck")
public class OrdercheckController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showOrderCheck(Model model) {
        Integer userId = getCurrentUserId();
        List<CartInfo> orderItems = orderService.getOrderItemsForUser(userId);
        UserInfo userInfo = userService.getUserInfoById(userId);

        model.addAttribute("orderItems", orderItems);
        model.addAttribute("userInfo", userInfo);
        return "ordercheck";
    }

    @PostMapping("/purchase")
    public String processPurchase(@RequestParam String paymentMethod, 
                                  @RequestParam(required = false) String cardNumber) {
        Integer userId = getCurrentUserId();
        orderService.processOrder(userId, paymentMethod, cardNumber);
        return "redirect:/confirmation";
    }

    @PostMapping("/cancel")
    public String cancelOrder() {
        return "redirect:/cart";
    }

    private Integer getCurrentUserId() {
        // 認証システムからユーザーIDを取得する実装
        return 1; // 仮の実装
    }
}

