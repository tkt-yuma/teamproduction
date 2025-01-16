package com.example.demo.user.controller;
//1月15日 遠所作業
//1/16 遠所作業 Serviceとの依存性をなくしました。

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.entity.UserInfo;

@Controller
public class AmendController {

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
        return "amend"; // amend.htmlを返す
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
            return "redirect:/amend";
        } else {
            model.addAttribute("error", "更新に失敗しました。");
            return "amend";
        }
    }

    private Integer getCurrentUserId() {
        // 認証システムからユーザーIDを取得する実装
        return 1; // 仮の実装
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
}

