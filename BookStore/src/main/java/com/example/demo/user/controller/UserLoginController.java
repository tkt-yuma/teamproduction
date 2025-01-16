package com.example.demo.user.controller;
//1月15日 遠所作業

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.user.entity.UserInfo; // ユーザー情報を保持するクラス
import com.example.demo.user.service.UserService;

@Controller
public class UserLoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "user-login"; // user-login.htmlを返す
    }

    @PostMapping("/login")
    public String login(UserInfo userInfo, Model model) {
        boolean isAuthenticated = userService.authenticateUser(userInfo.getUsername(), userInfo.getPassword());

        if (isAuthenticated) {
            // ログイン成功時の処理（セッションにユーザー情報を保存など）
            return "redirect:/"; // トップページへリダイレクト
        } else {
            model.addAttribute("error", "ユーザー名またはパスワードが間違っています。");
            return "user-login"; // ログイン失敗時、再度ログインフォームを表示
        }
    }
}

