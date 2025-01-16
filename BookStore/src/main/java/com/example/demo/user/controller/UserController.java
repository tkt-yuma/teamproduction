package com.example.demo.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.user.entity.CartInfo;
import com.example.demo.user.entity.Comment;
import com.example.demo.user.entity.ItemId;
import com.example.demo.user.entity.SaleManagement;
import com.example.demo.user.entity.UserInfo;
import com.example.demo.user.entity.UserLogin;
import com.example.demo.user.service.BookService;
import com.example.demo.user.service.CartService;
import com.example.demo.user.service.OrderService;
import com.example.demo.user.service.ReviewService;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.WantService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    private BookService bookService;
	
	@Autowired
    private CartService cartService;
	
	@Autowired
    private OrderService orderService;
	
	@Autowired
    private ReviewService reviewService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private WantService wantService;

    // ログアウト処理
    @GetMapping("/logout")
    public String logout() {
        // ログアウト処理（セッション無効化など）
        return "redirect:/user"; // トップページへリダイレクト
    }
    
    //topページへ移動
    @GetMapping("/top")
    public String goToTopPage() {
        return "user/product";
    }
    
    @GetMapping("/order")
    public String showOrderCheck(Model model) {
        Integer userId = getCurrentUserId();
        List<CartInfo> orderItems = orderService.getOrderItemsForUser(userId);
        UserInfo userInfo = userService.getUserInfoById(userId);

        model.addAttribute("orderItems", orderItems);
        model.addAttribute("userInfo", userInfo);
        return "user/ordercheck";
    }

    @PostMapping("/purchase")
    public String processPurchase(@RequestParam String paymentMethod, 
                                  @RequestParam(required = false) String cardNumber) {
        Integer userId = getCurrentUserId();
        orderService.processOrder(userId, paymentMethod, cardNumber);
        return "redirect:/user/confirmation";
    }

    @PostMapping("/cancel")
    public String cancelOrder() {
        return "redirect:/user/cart";
    }

    @PostMapping("/search")
    public String search(@RequestParam("q") String query, Model model) {
        model.addAttribute("searchResults", bookService.searchBooks(query));
        return "search";
    }

  	@GetMapping("/category")
    public String category(@RequestParam("name") String categoryName, Model model) {
        model.addAttribute("books", bookService.getBooksByCategory(categoryName));
        return "product"; // カテゴリ別の商品一覧も product.html を使用
    }

  	@PostMapping("/register")
    public String registerUser(UserInfo userInfo, Model model) {
        // ユーザー情報をサービスに渡して登録を処理
        boolean isRegistered = userService.registerUser(userInfo);
        
        if (isRegistered) {
            model.addAttribute("message", "登録が完了しました。ログインしてください。");
            return "redirect:/user/login"; // 登録成功後、ログインページへリダイレクト
        } else {
            model.addAttribute("error", "登録に失敗しました。");
            return "user/register"; // 登録失敗時、再度登録フォームを表示
        }
    }
    // 特定の商品に対するレビューを表示
    @PostMapping("/{itemId}/review")
    public String showReviews(@PathVariable Integer itemId, Model model) {
        List<Comment> reviews = reviewService.getReviewsForItem(itemId);
        model.addAttribute("review", reviews);
        model.addAttribute("itemId", itemId);
        return "user/review"; // review-list.htmlを返す
    }

    // レビュー作成フォームの表示（リダイレクト）
    @GetMapping("/new/{itemId}")
    public String showCreateReviewForm(@PathVariable Integer itemId) {
        return "redirect:/user/review/" + itemId; // 商品のレビュー一覧ページにリダイレクト
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
        return "redirect:/user/reviews/" + itemId; // 作成したレビューに基づいてリダイレクト
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/user-login"; // user-login.htmlを返す
    }

    @PostMapping("/login")
    public String login(UserLogin userlogin, Model model) {
        boolean isAuthenticated = userService.authenticateUser(userlogin.getUserMail(), userlogin.getUserPass());

        if (isAuthenticated) {
            // ログイン成功時の処理（セッションにユーザー情報を保存など）
            return "redirect:/user/product"; // トップページへリダイレクト
        } else {
            model.addAttribute("error", "ユーザー名またはパスワードが間違っています。");
            return "user/user-login"; // ログイン失敗時、再度ログインフォームを表示
        }
    }

    @GetMapping("/wishlist")
    public String showWishList(Model model) {
        List<CartInfo> wantItems = wantService.getWantItems(getCurrentUserId());
        model.addAttribute("wantItems", wantItems);
        return "user/wishlist";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("itemId") Integer itemId) {
        CartInfo item = wantService.getWantItem(getCurrentUserId(), itemId);
        cartService.addToCart(getCurrentUserId(), item);
        return "redirect:/user/wishlist";
    }

    @PostMapping("/wishlist-remove")
    public String removeFromWantList(@RequestParam("itemId") Integer itemId) {
        wantService.removeFromWantList(getCurrentUserId(), itemId);
        return "redirect:/user/wishlist";
    }
    
    // ユーザー情報を保持するための簡易的なストレージ
    private static Map<Integer, UserInfo> userStorage = new HashMap<>();

    static {
        // サンプルユーザーを追加
        UserInfo user = new UserInfo();
        user.setUserId(1);
        user.setUserRealName("山田太郎");
        user.setUserMail("yamada@example.com");
        user.setUserTel("090-1234-5678");
        user.setUserBirthday("1990-01-01");
        user.setUserCreditnum("1234-5678-9012-3456");
        user.setUserAddress("東京都渋谷区〇〇1-2-3");
        userStorage.put(1, user);
    }

    @GetMapping("/amend")
    public String showAmendForm(Model model) {
        Integer userId = getCurrentUserId();
        UserInfo userInfo = getUserInfoById(userId);
        
        model.addAttribute("userInfo", userInfo);
        return "user/amend"; // amend.htmlを返す
    }

    @PostMapping("/amend")
    public String amendUser(@RequestParam String username,
                            @RequestParam String email,
                            @RequestParam String address,
                            @RequestParam String phone,
                            @RequestParam String creditcard,
                            Model model) {
        Integer userId = getCurrentUserId();
        UserInfo userInfo = getUserInfoById(userId);
        
        userInfo.setUserRealName(username);
        userInfo.setUserMail(email);
        userInfo.setUserAddress(address);
        userInfo.setUserTel(phone);
        userInfo.setUserCreditnum(creditcard);

        boolean isUpdated = updateUser(userInfo);
        
        if (isUpdated) {
            model.addAttribute("message", "登録内容が更新されました。");
            return "redirect:/user/amend";
        } else {
            model.addAttribute("error", "更新に失敗しました。");
            return "user/amend";
        }
    }

    private UserInfo getUserInfoById(Integer userId) {
        // ユーザー情報を取得する実装
        return userStorage.get(userId);
    }

    private boolean updateUser(UserInfo userInfo) {
        // ユーザー情報を更新する実装
        if (userStorage.containsKey(userInfo.getUserId())) {
            userStorage.put(userInfo.getUserId(), userInfo);
            return true;
        }
        return false;
    }
    
    // カート情報を保持するための簡易的なストレージ
    private static Map<Integer, List<CartInfo>> cartStorage = new HashMap<>();
    private static int nextCartItemId = 1;

    @GetMapping("/cart")
    public String showCart(Model model) {
        List<CartInfo> cartItems = getCartItems(getCurrentUserId());
        double total = calculateTotal(getCurrentUserId());
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "user/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam("itemId") Integer itemId, 
                                 @RequestParam("quantity") Integer quantity) {
        updateItemQuantity(getCurrentUserId(), itemId, quantity);
        return "redirect:/user/cart";
    }

    @PostMapping("/cart-remove")
    public String removeItem(@RequestParam("itemId") Integer itemId) {
        removeItemFromCart(getCurrentUserId(), itemId);
        return "redirect:/user/cart";
    }

    @PostMapping("/ordercheck")
    public String purchase() {
        // 購入処理のロジックを実装
        return "redirect:/user/ordercheck";
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
    
    @GetMapping("/confirmation")
    public String showConfirmation(Model model) {
        Integer userId = getCurrentUserId();
        int estimatedDeliveryDays = orderService.getEstimatedDeliveryDays(userId);
        
        model.addAttribute("estimatedDeliveryDays", estimatedDeliveryDays);
        return "user/confirmation";
    }
    
    @PostMapping("/{itemId}/details")
    public String showItemDetails(@PathVariable Integer itemId, Model model) {
        ItemId item = getItemById(itemId);
        List<Comment> reviews = getCommentsForItem(itemId);
        model.addAttribute("item", item);
        model.addAttribute("reviews", reviews);
        return "user/details";
    }

    @PostMapping("/{itemId}/addToWishlist")
    @ResponseBody
    public String addToWishlist(@PathVariable Integer itemId) {
        // ほしいものリストに追加するロジックを直接実装
        return "ほしいものリストに追加しました";
    }

    // 以下、サービスの代わりに直接メソッドを実装

    private ItemId getItemById(Integer itemId) {
        // 商品を取得するロジックを直接実装
        ItemId item = new ItemId();
        item.setItemId(itemId);
        // その他の項目を設定
        return item;
    }

    private List<Comment> getCommentsForItem(Integer itemId) {
        // 商品に対するコメントを取得するロジックを直接実装
        List<Comment> comments = new ArrayList<>();
        // サンプルのコメントを追加
        Comment comment = new Comment();
        comment.setItemId(itemId);
        comment.setUserId(1);
        comment.setReview("サンプルレビュー");
        comment.setDate(new Date());
        comments.add(comment);
        return comments;
    }

    private List<ItemId> searchItems(String query) {
        // 商品を検索するロジックを直接実装
        List<ItemId> results = new ArrayList<>();
        // 検索結果のサンプルを追加
        ItemId item = new ItemId();
        item.setItemId(1);
        // その他の項目を設定
        results.add(item);
        return results;
    }
    
    @GetMapping("/history")
    public String showPurchaseHistory(Model model) {
        Integer userId = getCurrentUserId();
        List<SaleManagement> purchaseHistory = orderService.getPurchaseHistoryForUser(userId);
        
        model.addAttribute("purchaseHistory", purchaseHistory);
        return "user/history"; // history.htmlを返す
    }

    @GetMapping("/mypage")
    public String showMypage(Model model) {
        // ユーザー情報の取得（仮の実装）
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setUserRealName("テストユーザー");
        userInfo.setUserMail("test@example.com");
        userInfo.setUserTel("090-1234-5678");
        userInfo.setUserBirthday("1990-01-01");
        userInfo.setUserCreditnum("1234-5678-9012-3456");
        userInfo.setUserAddress("東京都渋谷区〇〇1-2-3");

        model.addAttribute("userInfo", userInfo);
        return "user/mypage";
    }

    @GetMapping("/edit")
    public String showEditPage() {
        return "user/amend";
    }
}
