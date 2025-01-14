package com.example.demo.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    // 仮の商品データ
    private List<Product> products = new ArrayList<>();

    public ProductController() {
        // 仮の商品データを追加
        products.add(new Product(1L, "Book 1", "Description 1", 1000, "Books"));
        products.add(new Product(2L, "Book 2", "Description 2", 1500, "Books"));
        products.add(new Product(3L, "Gadget 1", "Description 3", 2000, "Gadgets"));
        products.add(new Product(4L, "Gadget 2", "Description 4", 2500, "Gadgets"));
        // 他の商品も追加...
    }

    /**
     * 商品メニューを表示します。
     *
     * @param model モデル
     * @return 商品メニューのビュー名
     */
    @GetMapping("/product")
    public String product(Model model) {
        model.addAttribute("recommendedProducts", getRecommendedProducts());
        model.addAttribute("newArrivals", getNewArrivals());
        return "user/product"; // 商品メニューのビュー名
    }

    /**
     * おすすめ商品を取得します。
     *
     * @return おすすめ商品のリスト
     */
    private List<Product> getRecommendedProducts() {
        // 仮の実装：最初の2つの商品をおすすめとして返す
        return products.subList(0, Math.min(2, products.size()));
    }

    /**
     * 新着商品を取得します。
     *
     * @return 新着商品のリスト
     */
    private List<Product> getNewArrivals() {
        // 仮の実装：最後の2つの商品を新着として返す
        int start = Math.max(0, products.size() - 2);
        return products.subList(start, products.size());
    }
}

