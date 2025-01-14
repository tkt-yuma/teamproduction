package com.example.demo.user.controller;
//1月14日 遠所作業 クラス追加 一時的なものなので後で消します。

public class MenuItem {
    private String name; // メニュー名
    private String url;  // リンク先URL

    public MenuItem(String name, String url) {
        this.name = name;
        this.url = url;
    }

    // ゲッターとセッター
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


