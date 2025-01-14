package com.example.demo.user.service;
//1月14日 遠所作業

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.user.controller.CartItem;
import com.example.demo.user.controller.User;

@Service
public class UserService {

    // 仮のデータストア（実際にはデータベースを使用）
    private List<User> users = new ArrayList<>();
    private User currentUser; // 現在のユーザーを保持するための変数
    private List<CartItem> currentCartItems = new ArrayList<>(); // 現在のユーザーのカートアイテム

    public UserService() {
        // 仮のユーザーデータを追加
        users.add(new User("user1", "user1@example.com", "password1"));
        users.add(new User("user2", "user2@example.com", "password2"));
        
        // 最初のユーザーを現在のユーザーとして設定（仮の実装）
        currentUser = users.get(0);
        
        // 仮にカートアイテムを追加
        currentCartItems.add(new CartItem("Book 1", 1000, 1));
        currentCartItems.add(new CartItem("Gadget 1", 2000, 2));
    }

    /**
     * ユーザー情報を更新します。
     * 
     * @param username 新しいユーザー名
     * @param email 新しいメールアドレス
     * @param password 新しいパスワード
     */
    public void updateUserInfo(String username, String email, String password) {
        if (currentUser != null) {
            currentUser.setUsername(username);
            currentUser.setEmail(email);
            currentUser.setPassword(password); // 注意: 実際にはパスワードはハッシュ化すべきです
        }
    }

    /**
     * 現在のユーザー情報を取得します。
     * 
     * @return 現在のユーザー
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * 現在のユーザーのカートアイテムを取得します。
     * 
     * @return カートアイテムのリスト
     */
    public List<CartItem> getCurrentUserCart() {
        return new ArrayList<>(currentCartItems); // 現在のカートアイテムを返す
    }

    /**
     * ユーザー登録処理
     * 
     * @param username ユーザー名
     * @param email メールアドレス
     * @param password パスワード
     * @return 登録が成功した場合はtrue、失敗した場合はfalse
     */
    public boolean registerNewUser(String username, String email, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) || user.getEmail().equals(email)) {
                return false; // 重複があれば登録失敗
            }
        }
        
        User newUser = new User(username, email, password); // 注意: パスワードはハッシュ化すべきです
        users.add(newUser);
        currentUser = newUser; // 登録後、現在のユーザーとして設定
        return true; // 登録成功
    }
}
