package com.example.demo.user.controller;
//1月14日 遠所作業 クラス追加
//1月15日 遠所作業 作り直し (エラー原因 service未実装)(entityのCartinfoと繋げています)
//1/16 遠所作業 Serviceとの依存性をなくしました。

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.entity.CartInfo;

@Controller
@RequestMapping("/cart")
public class CartController {

    // カート情報を保持するための簡易的なストレージ
    private static Map<Integer, List<CartInfo>> cartStorage = new HashMap<>();
    private static int nextCartItemId = 1;

    @GetMapping
    public String showCart(Model model) {
        List<CartInfo> cartItems = getCartItems(getCurrentUserId());
        double total = calculateTotal(getCurrentUserId());
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam("itemId") Integer itemId, 
                                 @RequestParam("quantity") Integer quantity) {
        updateItemQuantity(getCurrentUserId(), itemId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam("itemId") Integer itemId) {
        removeItemFromCart(getCurrentUserId(), itemId);
        return "redirect:/cart";
    }

    @PostMapping("/purchase")
    public String purchase() {
        // 購入処理のロジックを実装
        return "redirect:/ordercheck";
    }

    // 以下、CartServiceのメソッドをコントローラー内に実装

    private List<CartInfo> getCartItems(Integer userId) {
        return cartStorage.getOrDefault(userId, new ArrayList<>());
    }

    private double calculateTotal(Integer userId) {
        // 注意: CartInfoにpriceフィールドがないため、合計金額の計算は実装できません
        // 実際のアプリケーションでは、別途商品価格情報を取得する必要があります
        return 0.0;
    }

    private void updateItemQuantity(Integer userId, Integer itemId, Integer quantity) {
        List<CartInfo> items = getCartItems(userId);
        for (CartInfo item : items) {
            if (item.getItemId().equals(itemId)) {
                item.setQuantity(quantity);
                break;
            }
        }
        cartStorage.put(userId, items);
    }

    private void removeItemFromCart(Integer userId, Integer itemId) {
        List<CartInfo> items = getCartItems(userId);
        items.removeIf(item -> item.getItemId().equals(itemId));
        cartStorage.put(userId, items);
    }

    // カートに商品を追加するメソッド（必要に応じて）
    private void addItemToCart(Integer userId, Integer itemId, String itemName, Integer quantity) {
        List<CartInfo> items = getCartItems(userId);
        CartInfo newItem = new CartInfo();
        newItem.setNum(nextCartItemId++);
        newItem.setUserId(userId);
        newItem.setItemId(itemId);
        newItem.setItemName(itemName);
        newItem.setQuantity(quantity);
        items.add(newItem);
        cartStorage.put(userId, items);
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

