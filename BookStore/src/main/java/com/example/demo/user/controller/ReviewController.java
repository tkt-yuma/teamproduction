package com.example.demo.user.controller;
//１月１５日　遠所作業

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.entity.Comment; // Commentクラスを使用
import com.example.demo.user.service.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 特定の商品に対するレビューを表示
    @GetMapping("/{itemId}")
    public String showReviews(@PathVariable Integer itemId, Model model) {
        List<Comment> reviews = reviewService.getReviewsForItem(itemId);
        model.addAttribute("reviews", reviews);
        model.addAttribute("itemId", itemId);
        return "review-list"; // review-list.htmlを返す
    }

    // レビュー作成フォームの表示（リダイレクト）
    @GetMapping("/new/{itemId}")
    public String showCreateReviewForm(@PathVariable Integer itemId) {
        return "redirect:/reviews/" + itemId; // 商品のレビュー一覧ページにリダイレクト
    }

    // 新しいレビューを作成
    @PostMapping("/new")
    public String createReview(@RequestParam Integer itemId,
                               @RequestParam String itemName,
                               @RequestParam Integer userId,
                               @RequestParam String review) {
        Comment comment = new Comment();
        comment.setItemId(itemId);
        comment.setItemName(itemName);
        comment.setUserId(userId);
        comment.setReview(review);
        comment.setDate(new Date()); // 現在の日付を設定
        
        reviewService.saveReview(comment); // Commentオブジェクトを保存
        return "redirect:/reviews/" + itemId; // 作成したレビューに基づいてリダイレクト
    }
    
 // マイページへのルーティング
    @GetMapping("/mypage")
    public String myPage() {
        return "review/mypage"; // mypage.htmlを返す
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


