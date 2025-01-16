package com.example.demo.user.controller;
//1月15日 遠所作業

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.user.entity.UserInfo; // UserInfoクラスを使用
import com.example.demo.user.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showRegistrationForm() {
        return "register"; // register.htmlを返す
    }

    @PostMapping
    public String registerUser(UserInfo userInfo, Model model) {
        // ユーザー情報をサービスに渡して登録を処理
        boolean isRegistered = userService.registerUser(userInfo);
        
        if (isRegistered) {
            model.addAttribute("message", "登録が完了しました。ログインしてください。");
            return "redirect:/login"; // 登録成功後、ログインページへリダイレクト
        } else {
            model.addAttribute("error", "登録に失敗しました。");
            return "register"; // 登録失敗時、再度登録フォームを表示
        }
    }
}

