package com.example.demo.user.controller;
//１月１５日　遠所作業(Service未実装想定)(entityのitemIdと繋げています)

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.user.entity.Comment; // Commentクラスを使用
import com.example.demo.user.entity.ItemId;
import com.example.demo.user.service.BookService;
import com.example.demo.user.service.CartService;
import com.example.demo.user.service.ReviewService;
import com.example.demo.user.service.WantService;

@Controller
@RequestMapping("/details")
public class DetailsController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    @Autowired
    private WantService wantService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{itemId}")
    public String showItemDetails(@PathVariable Integer itemId, Model model) {
        ItemId item = bookService.getItemById(itemId);
        List<Comment> reviews = reviewService.getCommentsForItem(itemId); // ReviewをCommentに変更
        model.addAttribute("item", item);
        model.addAttribute("reviews", reviews);
        return "details";
    }

    @PostMapping("/{itemId}/addToCart")
    @ResponseBody
    public String addToCart(@PathVariable Integer itemId, @RequestParam Integer quantity) {
        cartService.addToCart(getCurrentUserId(), itemId, quantity);
        return "カートに追加しました";
    }

    @PostMapping("/{itemId}/addToWishlist")
    @ResponseBody
    public String addToWishlist(@PathVariable Integer itemId) {
        wantService.addToWantList(getCurrentUserId(), itemId);
        return "ほしいものリストに追加しました";
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        List<ItemId> searchResults = itemService.searchItems(q);
        model.addAttribute("searchResults", searchResults);
        return "product";
    }

    @GetMapping("/{itemId}/writeReview")
    public String showWriteReviewForm(@PathVariable Integer itemId, Model model) {
        model.addAttribute("itemId", itemId);
        return "review";
    }

    private Integer getCurrentUserId() {
        // 認証システムからユーザーIDを取得する実装
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
