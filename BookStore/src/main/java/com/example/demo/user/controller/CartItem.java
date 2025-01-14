package com.example.demo.user.controller;
//1月14日 遠所作業 クラス追加 一時的なものなので後で消します。

public class CartItem {
    private String productName; // 商品名
    private double price;       // 価格
    private int quantity;       // 数量

    public CartItem(String productName, double price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    // ゲッターとセッター
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

