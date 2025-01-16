package com.example.demo.user.controller;
//1月15日 遠所作業 (ThymeleafとService未実装想定)(entityのuserinfoと繋げています)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.user.entity.UserInfo;
import com.example.demo.user.service.UserService;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private UserService userService;

    @GetMapping
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
        return "mypage";
    }

    @GetMapping("/edit")
    public String showEditPage() {
        return "amend";
    }

    @GetMapping("/top")
    public String goToTopPage() {
        return "product";
    }
}
