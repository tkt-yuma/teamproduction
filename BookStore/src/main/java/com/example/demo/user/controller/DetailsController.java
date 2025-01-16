package com.example.demo.user.controller;
//１月１５日　遠所作業(Service未実装想定)(entityのitemIdと繋げています)
//1/16 遠所作業 Serviceとの依存性をなくしました。下記コメントアウトに修正すべき内容あり。

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.user.entity.Comment;
import com.example.demo.user.entity.ItemId;

@Controller
@RequestMapping("/details")
public class DetailsController {

    @GetMapping("/{itemId}")
    public String showItemDetails(@PathVariable Integer itemId, Model model) {
        ItemId item = getItemById(itemId);
        List<Comment> reviews = getCommentsForItem(itemId);
        model.addAttribute("item", item);
        model.addAttribute("reviews", reviews);
        return "details";
    }

    @PostMapping("/{itemId}/addToCart")
    @ResponseBody
    public String addToCart(@PathVariable Integer itemId, @RequestParam Integer quantity) {
        // カートに追加するロジックを直接実装
        return "カートに追加しました";
    }

    @PostMapping("/{itemId}/addToWishlist")
    @ResponseBody
    public String addToWishlist(@PathVariable Integer itemId) {
        // ほしいものリストに追加するロジックを直接実装
        return "ほしいものリストに追加しました";
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        List<ItemId> searchResults = searchItems(q);
        model.addAttribute("searchResults", searchResults);
        return "product";
    }

    @GetMapping("/{itemId}/writeReview")
    public String showWriteReviewForm(@PathVariable Integer itemId, Model model) {
        model.addAttribute("itemId", itemId);
        return "review";
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

/*このコントローラーとHTMLは基本的に互いに機能すると思われますが、いくつかの点で調整が必要です：
商品詳細の表示:
コントローラーの showItemDetails メソッドは正しく実装されていますが、HTMLでは静的なデータが使用されています。Thymeleafを使用して動的にデータを表示するように修正する必要があります。
カートへの追加:
コントローラーの addToCart メソッドは実装されていますが、HTMLの対応するJavaScriptでは単にダイアログを表示するだけです。実際にサーバーにリクエストを送信するように修正が必要です。
ほしいものリストへの追加:
addToWishlist メソッドも同様に、HTMLとJavaScriptの調整が必要です。
検索機能:
コントローラーの search メソッドは実装されていますが、HTMLの検索フォームのアクションが設定されていません。
レビュー機能:
コントローラーには showWriteReviewForm メソッドがありますが、HTMLではアラートを表示するだけです。実際にレビューを書くページへリダイレクトするように修正が必要です。
ナビゲーションメニュー:
コントローラーにはマイページや履歴などへのルーティングが実装されていますが、HTMLのナビゲーションリンクは "#" になっています。適切なURLに更新する必要があります。
これらの点を調整すれば、コントローラーとHTMLは適切に連携して機能するはずです。
*/
