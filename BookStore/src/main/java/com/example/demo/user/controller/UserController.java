package com.example.demo.user.controller;

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

import com.example.demo.user.dto.CartDto;
import com.example.demo.user.dto.ItemDto;
import com.example.demo.user.dto.ReviewDto;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.CartInfo;
import com.example.demo.user.entity.Comment;
import com.example.demo.user.entity.ItemId;
import com.example.demo.user.entity.SaleManagement;
import com.example.demo.user.entity.UserInfo;
import com.example.demo.user.entity.UserLogin;
import com.example.demo.user.entity.Wishlist;
import com.example.demo.user.service.BookService;
import com.example.demo.user.service.CartService;
import com.example.demo.user.service.OrderService;
import com.example.demo.user.service.ReviewService;
import com.example.demo.user.service.UserService;
import com.example.demo.user.service.WantService;
import com.example.demo.utils.Utils;

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

//	@PostMapping("/userpublic/product") //topページへ移動(商品一覧画面が今回のtopページ)
//	public String goToTopPage() {
//		return "user/userpublic/product";
//	}

	@GetMapping("/register") //新規登録画面の表示
	public String showRegister() {
		return "user/userpublic/register";
	}

	@PostMapping("/register") //新規登録の処理
	public String registerUser(UserDto userDto, Model model) {
			UserInfo userInfo = userService.addUserInfo(userDto);
			UserLogin userLogin = userService.addUserLogin(userDto);
			model.addAttribute("message", userInfo);
			model.addAttribute("userlogin", userLogin);
			
			return "redirect:/user/userpublic/user-login"; //登録成功後、ログインページへリダイレクト
		
	}

	@GetMapping("/userpublic/user-login") //ログイン画面を表示
	public String showLoginForm() {
		return "user/userpublic/user-login"; //user-login.htmlを返す
	}

	
	@GetMapping("/userpublic/product") //ログアウト処理
	public String logout2(Model model) { //ログアウト処理（セッション無効化など）
		List<ItemId> list = bookService.findAll();
//		for(ItemId i : list) {
//			System.out.println(i.getItemName());
//		}
		model.addAttribute("itemlist", list);
		return "user/userpublic/product"; //トップページへリダイレクト
	}
	@PostMapping("/userpublic/product") //ログアウト処理
	public String logout() { //ログアウト処理（セッション無効化など）
		return "user/userpublic/product"; //トップページへリダイレクト
	}

	@GetMapping("/mypage") //マイページ画面の表示
	public String showMypage(Model model) {
		UserInfo user = userService.getUserInfoById(Utils.getUserId());
		model.addAttribute("userInfo", user);
		return "user/userprivate/mypage";
	}

	@GetMapping("/history") //購入履歴画面の表示
	public String showPurchaseHistory(Model model) {
		List<SaleManagement> purchaseHistory = orderService.getPurchaseHistoryForUser(Utils.getUserId());

		model.addAttribute("purchaseHistory", purchaseHistory);
		return "user/userprivate/history"; 
	}

	@GetMapping("/ordercheck") //購入確認画面の表示
	public String showOrderCheck(Model model) {
		List<CartInfo> orderItems = cartService.getCartItems(Utils.getUserId());
		UserInfo user = userService.getUserInfoById(Utils.getUserId());

		model.addAttribute("orderItems", orderItems);
		model.addAttribute("userInfo", user);
		return "user/userprivate/ordercheck";
	}

	@GetMapping("/confirmation") //購入完了画面の表示
	public String showConfirmation() {
		return "user/userprivate/confirmation";
	}

	@PostMapping("/confirmation") //購入処理の実行
	public String processPurchase() {
		orderService.processOrder(cartService.getCartItems(Utils.getUserId()));
		return "redirect:/user/userprivate/confirmation";
	}

	@PostMapping("/cancel") //注文キャンセル処理
	public String cancelOrder() {
		return "redirect:/user/userprivate/cart";
	}

	@GetMapping("/search") //検索画面を表示
	public String showSearchForm(Model model,ItemDto itemDto) {
		List<ItemId> Item = bookService.searchBooks(itemDto.getSearch());
		model.addAttribute("searchQuery", Item);
		return "redirect:/user/userpublic/search";  // 検索画面を表示するビューを返す
	}

	@GetMapping("/category/{name}") //カテゴリ別商品一覧を商品一覧画面に表示
	public String category(@PathVariable ("name")String categoryname, Model model) {
		List<ItemId> item = bookService.getBooksByCategory(categoryname);
		model.addAttribute("category", item);
		return "redirect:/user/userpublic/search";
	}

	@GetMapping("/new-reviews") //レビュー作成フォームの表示（リダイレクト）
	public String showCreateReviewForm() {
		return "user/userprivate/review/";
	}

	@PostMapping("/new-reviews") //新しいレビューを作成
	public String createReview(ReviewDto comment) {
		Integer userId = Utils.getUserId();
		reviewService.saveReview(comment,userId); //Commentオブジェクトを保存
		return "redirect:/user/userprivate/reviews/"; //作成したレビューに基づいてリダイレクト
	}

	@GetMapping("/wishlist") //欲しいものリストを表示
	public String showWishList(Model model) {
		List<Wishlist> wantItems = wantService.getWantItems(Utils.getUserId());
		model.addAttribute("wantItems", wantItems);
		return "user/userprivate/wishlist";
	}

	@PostMapping("/{itemId}/wishlist-addToCart") //欲しいものリストからカートに追加
	public String addToCartWishlist(@RequestParam("itemId") Integer itemId,CartDto cartDto) {
		cartService.addToCart(Utils.getUserId(), itemId,cartDto);
		return "redirect:/user/wishlist";
	}

	@PostMapping("/{itemId}/wishlist-remove") //欲しいものリストから削除
	public String removeFromWantList(@RequestParam("itemId") Integer itemId) {
		wantService.deleteWishList(Utils.getUserId(), itemId);
		return "redirect:/user/wishlist";
	}

	@GetMapping("/amend") //登録修正画面表示
	public String showAmendForm(Model model) {
		
		model.addAttribute("userInfo", userService.getUserInfoById(Utils.getUserId()));
		return "user/userprivate/amend";
	}

	@PostMapping("/amend") //登録修正処理
	public String amendUser(UserDto userDto,Model model) {

		UserInfo userInfo = userService.updateInfo(userDto) ;
		UserLogin userLogin = userService.updatePass(userDto.getUserPass());
		model.addAttribute("amend/user", userInfo);
		model.addAttribute("amend/pass", userLogin);
		return "redirect:/user/userprivate/mypage";
	}


	@GetMapping("/userpublic/{itemId}/details") //商品詳細ページの表示
	public String showItemDetails(@PathVariable("itemId") Integer itemId, Model model) {
		ItemId item = bookService.getItemById(itemId);
		List<Comment> reviews = reviewService.getReviewsForItem(itemId);
		//System.out.println(reviews.size());
		model.addAttribute("item", item);
		model.addAttribute("reviews", reviews);
		return "user/userpublic/details";
		//return "redirect:/user/userpublic/details";
	}

	@PostMapping("/{itemId}/addToWishlist") //商品詳細ページから欲しいものリストに追加
	@ResponseBody
	public String addToWishlist(@PathVariable Integer itemId) { //ほしいものリストに追加するロジックを直接実装
		return "ほしいものリストに追加しました";
	}

	@PostMapping("/{itemId}/details-addToCart") //商品詳細ページからカートに追加
	public String addToCartDetails(@RequestParam("itemId") Integer itemId,CartDto cartDto) {
		cartService.addToCart(Utils.getUserId(), itemId, cartDto);
		return "redirect:/user/details";
	}

	//以下Cart関連

	@GetMapping("/cart") //カート画面を表示
	public String showCart(Model model) {
		List<CartInfo> cartItems = cartService.getCartItems(Utils.getUserId());

		model.addAttribute("cartItems", cartItems);
		return "user/userprivate/cart";
	}

	@PostMapping("/{itemId}/cart-update") //カートの商品の数量を変更
	public String updateQuantity(@RequestParam("itemId") Integer itemId, CartDto cartDto) {
		cartService.updateItemQuantity(Utils.getUserId(), itemId, cartDto);
		return "redirect:/user/cart";
	}

	@PostMapping("/{itemId}/cart-remove") //カートから削除
	public String removeItem(@RequestParam("itemId") Integer itemId) {
		cartService.deleteItemCart(Utils.getUserId(), itemId);
		return "redirect:/user/cart";
	}

}
