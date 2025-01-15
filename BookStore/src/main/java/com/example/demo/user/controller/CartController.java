package com.example.demo.user.controller;
//1月14日 遠所作業 クラス追加
//1月15日 遠所作業 作り直し (エラー原因 service未実装)(entityのCartinfoと繋げています)

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.entity.CartInfo;
import com.example.demo.user.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String showCart(Model model) {
        List<CartInfo> cartItems = cartService.getCartItems(getCurrentUserId());
        double total = cartService.calculateTotal(getCurrentUserId());
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam("itemId") Integer itemId, 
                                 @RequestParam("quantity") Integer quantity) {
        cartService.updateItemQuantity(getCurrentUserId(), itemId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam("itemId") Integer itemId) {
        cartService.removeItem(getCurrentUserId(), itemId);
        return "redirect:/cart";
    }

    @PostMapping("/purchase")
    public String purchase() {
        // 購入処理のロジックを実装
        return "redirect:/ordercheck";
    }

    // 現在ログインしているユーザーのIDを取得するメソッド（実装が必要）
    private Integer getCurrentUserId() {
        // ここに認証情報からユーザーIDを取得する実装を追加
        return 1; // 仮の実装
    }
    
 // マイページへのルーティング
    @GetMapping("/mypage")
    public String myPage() {
        return "mypage"; // mypage.htmlを返す
    }

    // 購入履歴へのルーティング
    @GetMapping("/history")
    public String history() {
        return "history"; // history.htmlを返す
    }

    // ほしいものリストへのルーティング
    @GetMapping("/want")
    public String wishlist() {
        return "want"; // want.htmlを返す
    }

    // プロフィール変更へのルーティング
    @GetMapping("/amend")
    public String amendProfile() {
        return "amend"; // amend.htmlを返す
    }

    // カートへのルーティング
    @GetMapping("/cart")
    public String cart() {
        return "cart"; // cart.htmlを返す
    }

    // ログアウト処理
    @GetMapping("/logout")
    public String logout() {
        // ログアウト処理（セッション無効化など）
        return "redirect:/"; // トップページへリダイレクト
    }
}

