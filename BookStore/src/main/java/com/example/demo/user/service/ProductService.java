package com.example.demo.user.service;
//1月14日 遠所作業 クラス追加

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.user.controller.Product;

@Service
public class ProductService {

    // 仮のデータストア（実際にはデータベースを使用）
    private List<Product> products = new ArrayList<>();

    public ProductService() {
        // 仮のデータを追加
        products.add(new Product(1L, "Book 1", "Description 1", 1000, "Books"));
        products.add(new Product(2L, "Book 2", "Description 2", 1500, "Books"));
        products.add(new Product(3L, "Gadget 1", "Description 3", 2000, "Gadgets"));
        products.add(new Product(4L, "Gadget 2", "Description 4", 2500, "Gadgets"));
        // 他の商品も追加...
    }

    /**
     * 全ての商品を取得します。
     * 
     * @param category カテゴリ名（nullの場合は全商品を返す）
     * @return 商品のリスト
     */
    public List<Product> getAllProducts(String category) {
        if (category == null || category.isEmpty()) {
            return products; // カテゴリが指定されていない場合は全商品を返す
        }
        
        // 指定されたカテゴリの商品をフィルタリングして返す
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    /**
     * 商品を追加します。
     * 
     * @param product 追加する商品
     * @return 追加された商品
     */
    public Product addProduct(Product product) {
        products.add(product);
        return product;
    }

    /**
     * 商品IDで指定された商品を取得します。
     * 
     * @param id 商品ID
     * @return 商品
     */
    public Product getProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * 商品情報を更新します。
     * 
     * @param id 更新する商品のID
     * @param updatedProduct 更新後の商品情報
     */
    public void updateProduct(Long id, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, updatedProduct);
                return;
            }
        }
    }

    /**
     * 商品を削除します。
     * 
     * @param id 削除する商品のID
     */
    public void deleteProduct(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }
    
    /**
     * カテゴリ別の商品リストを取得します。
     *
     * @param category カテゴリ名
     * @return 指定されたカテゴリの商品リスト
     */
    public List<Product> getProductsByCategory(String category) {
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    /**
     * おすすめ商品を取得します。
     *
     * @return おすすめ商品のリスト
     */
    public List<Product> getRecommendedProducts() {
        // 仮の実装：最初の2つの商品をおすすめとして返す
        return products.subList(0, Math.min(2, products.size()));
    }

    /**
     * 新着商品を取得します。
     *
     * @return 新着商品のリスト
     */
    public List<Product> getNewArrivals() {
        // 仮の実装：最後の2つの商品を新着として返す
        int start = Math.max(0, products.size() - 2);
        return products.subList(start, products.size());
    }

    /**
     * 商品ランキングを取得します。
     *
     * @return 商品ランキングのリスト
     */
    public List<Product> getRankedProducts() {
        // 仮の実装：全商品をそのまま返す（実際にはランキングロジックが必要）
        return new ArrayList<>(products); // 全商品を返す（ここにランキングロジックを追加）
    }
}



