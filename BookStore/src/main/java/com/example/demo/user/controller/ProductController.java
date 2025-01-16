package com.example.demo.user.controller;
//1月14日 遠所作業
//1月15日 遠所作業 作り直し (エラー原因 service未実装)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.service.BookService;
import com.example.demo.user.service.UserService;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private BookService bookService; //仮のService

    @Autowired
    private UserService userService; //仮のService

    @GetMapping("/toppage")
    public String home(Model model) {
        model.addAttribute("recommendedBooks", bookService.getRecommendedBooks());
        model.addAttribute("newBooks", bookService.getNewBooks());
        model.addAttribute("rankingBooks", bookService.getRankingBooks());
        return "product"; // メインページは product.html
    }

    @GetMapping("/mypage")
    public String myPage() {
        return "mypage";
    }

    @GetMapping("/history")
    public String history() {
        return "history";
    }

    @GetMapping("/want")
    public String wishlist() {
        return "want";
    }

    @GetMapping("/amend")
    public String amendProfile() {
        return "amend";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/logout")
    public String logout() {
        // ログアウト処理
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }

    @PostMapping("/search")
    public String search(@RequestParam("q") String query, Model model) {
        model.addAttribute("searchResults", bookService.searchBooks(query));
        return "search";
    }

    @GetMapping("/details")
    public String bookDetails(@RequestParam("id") Long bookId, Model model) {
        model.addAttribute("book", bookService.getBookById(bookId));
        return "details";
    }

    @GetMapping("/review")
    public String review(@RequestParam("id") Long bookId, Model model) {
        model.addAttribute("book", bookService.getBookById(bookId));
        return "review";
    }

    @GetMapping("/ordercheck")
    public String orderCheck() {
        return "ordercheck";
    }

    @GetMapping("/confirmation")
    public String confirmation() {
        return "confirmation";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "user-login";
    }

    @GetMapping("/category")
    public String category(@RequestParam("name") String categoryName, Model model) {
        model.addAttribute("books", bookService.getBooksByCategory(categoryName));
        return "product"; // カテゴリ別の商品一覧も product.html を使用
    }
}