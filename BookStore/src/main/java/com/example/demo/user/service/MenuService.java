package com.example.demo.user.service;
//1月14日 遠所作業 クラス追加

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.user.controller.MenuItem;

@Service
public class MenuService {

    // 仮のメニュー項目データ
    private List<MenuItem> menuItems = new ArrayList<>();

    public MenuService() {
        // 仮のメニュー項目を追加
        menuItems.add(new MenuItem("Home", "/user/mypage"));
        menuItems.add(new MenuItem("Products", "/user/products"));
        menuItems.add(new MenuItem("Cart", "/user/cart"));
        menuItems.add(new MenuItem("Wishlist", "/user/wishlist"));
        menuItems.add(new MenuItem("Profile", "/user/update-info"));
        // 他のメニュー項目も追加...
    }

    /**
     * メニュー項目を取得します。
     *
     * @return メニュー項目のリスト
     */
    public List<MenuItem> getMenuItems() {
        return new ArrayList<>(menuItems); // メニュー項目のリストを返す
    }
}




