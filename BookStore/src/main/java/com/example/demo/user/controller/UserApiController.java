package com.example.demo.user.controller;
//1月14日 遠所作業 クラス追加 API関連はこちらに書く

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.service.MenuService;
import com.example.demo.user.service.ProductService;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MenuService menuService; // MenuServiceを追加

    /**
     * カテゴリ別の商品リストを取得します。
     *
     * @param category カテゴリ名
     * @return 商品リスト
     */
    @GetMapping("/products/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/recommended") // 非同期データ読み込みのサポート
    public ResponseEntity<List<Product>> getRecommendedProducts() {
        List<Product> products = productService.getRecommendedProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/new-arrivals")
    public ResponseEntity<List<Product>> getNewArrivals() {
        List<Product> products = productService.getNewArrivals();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<Product>> getRankedProducts() {
        List<Product> products = productService.getRankedProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/menu-items") // メニュー項目の動的取得
    public ResponseEntity<List<MenuItem>> getMenuItems() {
        List<MenuItem> menuItems = menuService.getMenuItems();
        return ResponseEntity.ok(menuItems);
    }
}

