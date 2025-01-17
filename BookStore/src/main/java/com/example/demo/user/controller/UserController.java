package com.example.demo.user.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/top") //topページへ移動(商品一覧画面が今回のtopページ)
    public String goToTopPage() {
        return "user/product";
    }
    
    @PostMapping("/register") //新規登録の処理
    public String registerUser(UserInfo userInfo, Model model) {
        boolean isRegistered = userService.registerUser(userInfo); //ユーザー情報をサービスに渡して登録を処理
        
        if (isRegistered) {
            model.addAttribute("message", "登録が完了しました。ログインしてください。");
            return "redirect:/user/login"; //登録成功後、ログインページへリダイレクト
        } else {
            model.addAttribute("error", "登録に失敗しました。");
            return "user/register"; //登録失敗時、再度登録フォームを表示
        }
    }
    
	@GetMapping("/login") //ログイン画面を表示
    public String showLoginForm() {
        return "user/user-login"; //user-login.htmlを返す
    }

    @PostMapping("/login") //ログインの処理
    public String login(UserLogin userlogin, Model model) {
        boolean isAuthenticated = userService.authenticateUser(userlogin.getUserMail(), userlogin.getUserPass());

        if (isAuthenticated) { //ログイン成功時の処理（セッションにユーザー情報を保存など）
            return "redirect:/user/product"; //トップページへリダイレクト
        } else {
            model.addAttribute("error", "ユーザー名またはパスワードが間違っています。");
            return "user/user-login"; //ログイン失敗時、再度ログインフォームを表示
        }
    }
    
    @PostMapping("/logout") //ログアウト処理
    public String logout() {
        // ログアウト処理（セッション無効化など）
        return "redirect:/user"; // トップページへリダイレクト
    }

    @GetMapping("/mypage") //マイページ画面の表示
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
    
    @GetMapping("/history") //購入履歴画面の表示
    public String showPurchaseHistory(Model model) {
        Integer userId = getCurrentUserId();
        List<SaleManagement> purchaseHistory = orderService.getPurchaseHistoryForUser(userId);
        
        model.addAttribute("purchaseHistory", purchaseHistory);
        return "user/history"; // history.htmlを返す
    }
    
    @GetMapping("/ordercheck") //購入確認画面の表示
    public String showOrderCheck(Model model) {
        Integer userId = getCurrentUserId();
        List<CartInfo> orderItems = orderService.getOrderItemsForUser(userId);
        UserInfo userInfo = userService.getUserInfoById(userId);

        model.addAttribute("orderItems", orderItems);
        model.addAttribute("userInfo", userInfo);
        return "user/ordercheck";
    }
    
    @PostMapping("/ordercheck") //購入確認画面
    public String purchase() { //購入処理のロジックを実装
        return "redirect:/user/ordercheck";
    }
    
    @GetMapping("/confirmation") //購入完了画面の表示
    public String showConfirmation(Model model) {
        Integer userId = getCurrentUserId(); //現在ログインしているユーザーのIDを取得します。
        int estimatedDeliveryDays = orderService.getEstimatedDeliveryDays(userId); //orderServiceを使用して、ユーザーの注文の推定配送日数を取得します。
        
        model.addAttribute("estimatedDeliveryDays", estimatedDeliveryDays); //取得した推定配送日数をモデルに追加します。これにより、ビュー（テンプレート）でこの値を使用できるようになります。
        return "user/confirmation";
    }

    @PostMapping("/confirmation") //購入処理の実行
    public String processPurchase(@RequestParam String paymentMethod, //支払い方法を示すパラメータ（必須）
                                  @RequestParam(required = false) String cardNumber) { //クレジットカード番号を示すパラメータ（任意）
        Integer userId = getCurrentUserId(); //現在ログインしているユーザーのIDを取得します。
        orderService.processOrder(userId, paymentMethod, cardNumber);
        return "redirect:/user/confirmation";
    }

    @PostMapping("/cancel") //注文キャンセル処理
    public String cancelOrder() {
        return "redirect:/user/cart";
    }

    @GetMapping("/search") //検索結果を表示するメソッド
    public String search(@RequestParam("q") String query, 
                         @RequestParam(defaultValue = "1") int page, 
                         Model model) {
        int pageSize = 10; //1ページあたりの商品数
        List<ItemId> allItems = searchItems(query); //既存のsearchItemsメソッドを使用
        
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, allItems.size());
        List<ItemId> pageItems = allItems.subList(start, end);
        
        model.addAttribute("searchQuery", query);
        model.addAttribute("searchResults", pageItems);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) allItems.size() / pageSize));
        return "user/search";
    }
    
    @PostMapping("/search") //検索処理
    public String search(@RequestParam("q") String query, Model model) {
        model.addAttribute("searchResults", bookService.searchBooks(query));
        return "user/search";
    }
    
    @GetMapping("/category/{name}") //カテゴリ別商品一覧を商品一覧画面に表示
    public String category(@PathVariable("name") String categoryName, 
                           @RequestParam(defaultValue = "1") int page, 
                           Model model) {
        int pageSize = 10; //1ページあたりの商品数
        List<ItemId> allCategoryItems = getCategoryItems(categoryName); //新しいメソッドを作成
        
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, allCategoryItems.size());
        List<ItemId> pageItems = allCategoryItems.subList(start, end);
        
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("books", pageItems);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) allCategoryItems.size() / pageSize));
        return "user/product"; //カテゴリーページが不明 商品一覧画面に遷移
    }
    
    @ModelAttribute("categories")
    public List<String> getCategories() { //カテゴリのリストを直接返す
        return Arrays.asList("ビジネス", "経済", "自己啓発", "歴史", "地理", "科学", "技術", "芸術", "音楽", "料理");
    }
    
    private List<ItemId> getCategoryItems(String categoryName) { //カテゴリ別の商品を取得
        //ここでカテゴリ別の商品リストを生成するロジックを実装
        //例: データベースから取得するか、ハードコードしたリストを返す
        List<ItemId> items = new ArrayList<>();
        //items にカテゴリに応じた ItemId オブジェクトを追加
        return items;
    }
    
  	@GetMapping("/category")
    public String category(@RequestParam("name") String categoryName, Model model) {
        model.addAttribute("books", bookService.getBooksByCategory(categoryName));
        return "product"; //カテゴリ別の商品一覧も product.html を使用
    }

    @PostMapping("/{itemId}/review") //特定の商品に対するレビューを表示
    public String showReviews(@PathVariable Integer itemId, Model model) {
        List<Comment> reviews = reviewService.getReviewsForItem(itemId);
        model.addAttribute("review", reviews);
        model.addAttribute("itemId", itemId);
        return "user/review"; //review.htmlを返す
    }

    @GetMapping("/new-reviews/{itemId}") //レビュー作成フォームの表示（リダイレクト）
    public String showCreateReviewForm(@PathVariable Integer itemId) {
        return "redirect:/user/review/" + itemId; //商品のレビュー一覧ページにリダイレクト
    }

    @PostMapping("/new-reviews") //新しいレビューを作成
    public String createReview(@RequestParam Integer itemId,
                               @RequestParam String itemName,
                               @RequestParam Integer userId,
                               @RequestParam String review) {
        Comment comment = new Comment();
        comment.setItemId(itemId);
        comment.setItemName(itemName);
        comment.setUserId(userId);
        comment.setReview(review);
        comment.setDate(new Date()); //現在の日付を設定
        
        reviewService.saveReview(comment); //Commentオブジェクトを保存
        return "redirect:/user/reviews/" + itemId; //作成したレビューに基づいてリダイレクト
    }

    @GetMapping("/wishlist") //欲しいものリストを表示
    public String showWishList(Model model) {
        List<CartInfo> wantItems = wantService.getWantItems(getCurrentUserId());
        model.addAttribute("wantItems", wantItems);
        return "user/wishlist";
    }

    @PostMapping("/wishlist-addToCart") //欲しいものリストからカートに追加
    public String addToCartWishlist(@RequestParam("itemId") Integer itemId) {
        CartInfo item = wantService.getWantItem(getCurrentUserId(), itemId);
        cartService.addToCart(getCurrentUserId(), item);
        return "redirect:/user/wishlist";
    }

    @PostMapping("/wishlist-remove") //欲しいものリストから削除
    public String removeFromWantList(@RequestParam("itemId") Integer itemId) {
        wantService.removeFromWantList(getCurrentUserId(), itemId);
        return "redirect:/user/wishlist";
    }
    
    @GetMapping("/amend") //登録修正画面表示
    public String showAmendForm(Model model) {
        Integer userId = getCurrentUserId();
        UserInfo userInfo = getUserInfoById(userId);
        
        model.addAttribute("userInfo", userInfo);
        return "user/amend";
    }

    @PostMapping("/amend") //登録修正処理
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

    @GetMapping("/{itemId}/details") //商品詳細ページの表示
    public String showItemDetails(@PathVariable Integer itemId, Model model) {
        ItemId item = getItemById(itemId);
        List<Comment> reviews = getCommentsForItem(itemId);
        model.addAttribute("item", item);
        model.addAttribute("reviews", reviews);
        return "user/details";
    }

    @PostMapping("/{itemId}/addToWishlist") //商品詳細ページから欲しいものリストに追加
    @ResponseBody
    public String addToWishlist(@PathVariable Integer itemId) { //ほしいものリストに追加するロジックを直接実装
        return "ほしいものリストに追加しました";
    }
    
    @PostMapping("/details-addToCart") //商品詳細ページからカートに追加
    public String addToCartDetails(@RequestParam("itemId") Integer itemId) {
        CartInfo item = wantService.getWantItem(getCurrentUserId(), itemId);
        cartService.addToCart(getCurrentUserId(), item);
        return "redirect:/user/details";
    }

    // 以下、detailsに使うためのもの。Serviceの代わりに直接メソッドを実装

    private ItemId getItemById(Integer itemId) { //商品を取得するロジックを直接実装
        ItemId item = new ItemId();
        item.setItemId(itemId);
        // その他の項目を設定
        return item;
    }

    private List<Comment> getCommentsForItem(Integer itemId) { //商品に対するコメントを取得するロジックを直接実装
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment(); //サンプルのコメントを追加
        comment.setItemId(itemId);
        comment.setUserId(1);
        comment.setReview("サンプルレビュー");
        comment.setDate(new Date());
        comments.add(comment);
        return comments;
    }

    private List<ItemId> searchItems(String query) { //商品を検索するロジックを直接実装
        List<ItemId> results = new ArrayList<>();
        ItemId item = new ItemId(); //検索結果のサンプルを追加
        item.setItemId(1);
        // その他の項目を設定
        results.add(item);
        return results;
    }
    
    //ユーザー情報を保持するための簡易的なストレージ
    private static Map<Integer, UserInfo> userStorage = new HashMap<>();

    static {
        UserInfo user = new UserInfo(); //サンプルユーザーを追加
        user.setUserId(1);
        user.setUserRealName("山田太郎");
        user.setUserMail("yamada@example.com");
        user.setUserTel("090-1234-5678");
        user.setUserBirthday("1990-01-01");
        user.setUserCreditnum("1234-5678-9012-3456");
        user.setUserAddress("東京都渋谷区〇〇1-2-3");
        userStorage.put(1, user);
    }
    
    private UserInfo getUserInfoById(Integer userId) { //ユーザー情報を取得する実装
        return userStorage.get(userId);
    }

    private boolean updateUser(UserInfo userInfo) { //ユーザー情報を更新する実装
        if (userStorage.containsKey(userInfo.getUserId())) {
            userStorage.put(userInfo.getUserId(), userInfo);
            return true;
        }
        return false;
    }
    
    //以下Cart関連
    
    //カート情報を保持するための簡易的なストレージ
    private static Map<Integer, List<CartInfo>> cartStorage = new HashMap<>();
    private static int nextCartItemId = 1;

    @GetMapping("/cart") //カート画面を表示
    public String showCart(Model model) {
        List<CartInfo> cartItems = getCartItems(getCurrentUserId());
        double total = calculateTotal(getCurrentUserId());
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "user/cart";
    }

    @PostMapping("/cart-update") //カートの商品の数量を変更
    public String updateQuantity(@RequestParam("itemId") Integer itemId, 
                                 @RequestParam("quantity") Integer quantity) {
        updateItemQuantity(getCurrentUserId(), itemId, quantity);
        return "redirect:/user/cart";
    }

    @PostMapping("/cart-remove") //カートから削除
    public String removeItem(@RequestParam("itemId") Integer itemId) {
        removeItemFromCart(getCurrentUserId(), itemId);
        return "redirect:/user/cart";
    }

    // 以下、CartServiceのメソッドをコントローラー内に実装

    private List<CartInfo> getCartItems(Integer userId) {
        return cartStorage.getOrDefault(userId, new ArrayList<>());
    }

    private double calculateTotal(Integer userId) {
        //注意: CartInfoにpriceフィールドがないため、合計金額の計算は実装できません
        //実際のアプリケーションでは、別途商品価格情報を取得する必要があります
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

    //カートに商品を追加するメソッド（必要に応じて）
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

    private Integer getCurrentUserId() { //現在ログインしているユーザーのIDを取得するメソッド（実装が必要）
        //ここに認証情報からユーザーIDを取得する実装を追加
        return 1; //仮の実装
    }
}
