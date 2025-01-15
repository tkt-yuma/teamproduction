package com.example.demo.user.controller;
//1月15日 遠所作業 (エラー原因 service未実装)

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
import com.example.demo.user.service.WantService;

@Controller
@RequestMapping("/want")
public class WantController {

    @Autowired
    private WantService wantService;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String showWantList(Model model) {
        List<CartInfo> wantItems = wantService.getWantItems(getCurrentUserId());
        model.addAttribute("wantItems", wantItems);
        return "want";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("itemId") Integer itemId) {
        CartInfo item = wantService.getWantItem(getCurrentUserId(), itemId);
        cartService.addToCart(getCurrentUserId(), item);
        return "redirect:/want";
    }

    @PostMapping("/remove")
    public String removeFromWantList(@RequestParam("itemId") Integer itemId) {
        wantService.removeFromWantList(getCurrentUserId(), itemId);
        return "redirect:/want";
    }

    @GetMapping("/top")
    public String goToTopPage() {
        return "redirect:/";
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


