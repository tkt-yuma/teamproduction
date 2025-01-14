package com.example.demo.user.controller;
//1月14日 遠所作業 クラス追加

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    // カートアイテムを保持するリスト
    private List<CartItem> cartItems = new ArrayList<>();

    // コンストラクタ
    public CartController() {
        // 仮のカートアイテムを追加
        cartItems.add(new CartItem("Book 1", 1000, 1));
        cartItems.add(new CartItem("Gadget 1", 2000, 2));
    }

    /**
     * カートページを表示します。
     *
     * @param model モデル
     * @return カートページのビュー名
     */
    @GetMapping("/cart")
    public String cart(Model model) {
        // ユーザーのカートの内容を取得
        model.addAttribute("cartItems", cartItems); // モデルにカートアイテムを追加
        return "user/cart"; // カートページのビュー名
    }
}
