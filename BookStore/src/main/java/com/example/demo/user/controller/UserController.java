package com.example.demo.user.controller;
// 1月14日 遠所作業 （大まかなメソッド追加　例外処理未実装　セキュリティー想定なし）

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.service.MenuService;
import com.example.demo.user.service.ProductService;
import com.example.demo.user.service.UserService;
/*@Controller
public class UserController {
	
	@GetMapping("product")
	public String details() {
		return "user/product";
	}
*/

@Controller
@RequestMapping("/user")
public class UserController {

	private static final String VIEW_PRODUCT = "user/product";
    private static final String VIEW_CART = "user/cart";
    // 他のビュー名も同様に定数として定義
    
	@Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;
    
    @GetMapping("/mypage") //マイページ(これはまだhtmlがない)
    public String myPage(Model model) {
        // ユーザー情報を取得してmodelに追加
        return "user/mypage";
    }
    
    @GetMapping("/history") //購入履歴ページ(これはまだhtmlがない)
    public String purchaseHistory(Model model) {
        // 購入履歴を取得してmodelに追加
        return "user/history";
    }
    
    @GetMapping("/wishlist") //欲しいものリストページ(これはまだhtmlがない)
    public String wishList(Model model) {
        // ほしいものリストを取得してmodelに追加
        return "user/wishlist";
    }
    
 // ユーザー情報更新フォームの表示
    @GetMapping("/update-info")
    public String updateInfoForm() {
        return "user/update-info"; // 更新フォームのビュー名
    }

    // ユーザー情報の更新処理
    @PostMapping("/update-info")
    public String updateInfo(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             Model model) {
        // 簡単なバリデーション
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "すべてのフィールドを入力してください。");
            return "user/update-info"; // エラーがある場合はフォームに戻る
        }

        // ユーザー情報を更新する処理（仮のメソッド）
        userService.updateUserInfo(username, email, password);

        return "redirect:/user/mypage"; // 更新後はマイページへリダイレクト
    }

    @GetMapping("/product/{id}") //商品詳細ページ
    public String getProductDetails(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "user/product-details";
    }
    
    @GetMapping("/recommended") //productメニューにあるオススメ商品の取得
    public String getRecommendedProducts(Model model) {
        // オススメ商品のリストを取得
        // modelに追加
        return "user/recommended";
    }
    
    @GetMapping("/new-arrivals") //productメニューにある新着商品の取得
    public String getNewArrivals(Model model) {
        // 新着商品のリストを取得
        // modelに追加
        return "user/new-arrivals";
    }
    
    // 商品リストの表示
    @GetMapping("/products")
    public String getProducts(@RequestParam(defaultValue = "0") int page, 
                              @RequestParam(defaultValue = "10") int size, 
                              @RequestParam(required = false) String category, 
                              Model model) {
        // 商品リストを取得
        List<Product> allProducts = productService.getAllProducts(category);
        
        // 手動でページネーションを実装
        int totalProducts = allProducts.size();
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalProducts);
        
        // 現在のページの商品リストを取得
        List<Product> products = allProducts.subList(startIndex, endIndex);
        
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalProducts / size));
        
        return "user/products"; // 商品リストのビュー名
    }
    
    @GetMapping("/products/filter") //価格でのフィルタリングをする際(検索機能の一つとして)
    public String filterProducts(@RequestParam(required = false) Integer minPrice, 
                                 @RequestParam(required = false) Integer maxPrice, 
                                 Model model) {
        // 価格範囲で商品をフィルタリング
        // modelに追加
        return "user/filtered-products";
    }

    @GetMapping("/categories") //カテゴリ一覧の取得
    public String getAllCategories(Model model) {
        // すべてのカテゴリリストを取得し、modelに追加
        return "user/categories";
    }

    @GetMapping("/ranking") //ランキング機能の実装
    public String getProductRanking(Model model) {
        // 商品ランキングのリストを取得し、modelに追加
        return "user/ranking";
    }

    @GetMapping("/ordercheck") //購入確認ページ
    public String orderCheck() {
        return "user/ordercheck";
    }

    @GetMapping("/confirmation") //購入完了ページ
    public String confirmation() {
        return "user/confirmation";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        // 簡単なバリデーション
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "すべてのフィールドを入力してください。");
            return "user/register";
        }
        
        // ユーザー登録処理
        boolean registrationSuccess = userService.registerNewUser(username, email, password);
        
        if (registrationSuccess) {
            return "redirect:/user/login";
        } else {
            model.addAttribute("error", "登録に失敗しました。もう一度お試しください。");
            return "user/register";
        }
    }

    @GetMapping("/search") //検索ページ
    public String search() {
        return "user/search";
    }
    
    @PostMapping("/search")
    public String searchProducts(@RequestParam("q") String keyword, Model model) {
        // 検索ロジックを実装
        // 検索結果をmodelに追加
        return "user/search-results";
    }

    @GetMapping("/login") //ログインページ
    public String loginForm() {
        return "user/user-login";
    }

    @PostMapping("/login")
    public String login() {
        // ログイン処理
        return "redirect:/user/product";
    }
    
    @PostMapping("/logout") //ログアウト
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    } 
    
}