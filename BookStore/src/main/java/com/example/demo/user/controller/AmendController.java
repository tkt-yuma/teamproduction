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
public class AmendController {

    @Autowired
    private UserService userService;

    @GetMapping("/amend")
    public String showAmendForm(Model model) {
        Integer userId = getCurrentUserId();
        UserInfo userInfo = userService.getUserInfoById(userId);
        
        model.addAttribute("userInfo", userInfo);
        return "amend"; // amend.htmlを返す
    }

    @PostMapping("/amend")
    public String amendUser(UserInfo userInfo, Model model) {
        boolean isUpdated = userService.updateUser(userInfo);
        
        if (isUpdated) {
            model.addAttribute("message", "登録内容が更新されました。");
            return "redirect:/amend"; // 変更フォームにリダイレクト（再度表示）
        } else {
            model.addAttribute("error", "更新に失敗しました。");
            return "amend"; // 更新失敗時、再度変更フォームを表示
        }
    }

    private Integer getCurrentUserId() {
        // 認証システムからユーザーIDを取得する実装
        return 1; // 仮の実装
    }
}

