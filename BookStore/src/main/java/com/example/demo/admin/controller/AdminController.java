package com.example.demo.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.admin.dto.AdminDto;
import com.example.demo.admin.entity.Item;
import com.example.demo.admin.entity.PurchaseManage;
import com.example.demo.admin.entity.Salemanage;
import com.example.demo.admin.service.AdminService;
//1/17吉岡編集

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@PostMapping("/order")
	public String itemOrder(@RequestBody AdminDto adminDto,Model model) {
		adminService.AddToOrder(adminDto);
		List<PurchaseManage> purchase = adminService.SelectOrderAll();
		model.addAttribute("order", purchase);
		return "redirect/:admin/order-managemen";
	}
	

	//トップページへ
	@GetMapping("/admintop")
	public String adminTop() {
		return "admin/admin-top";
	}

	//ログインページへ
	@GetMapping("/adminlogin")
	public String adminLogin() {
		return "admin/admin-login";
	}

	//在庫画面へ
	@GetMapping("/inventory")
	public String adminInventory(Model model) {
		List<Item> item = adminService.SelectItemAll();
		model.addAttribute("item", item);
		return "admin/inventory";
	}

	//注文履歴へ
	@GetMapping("/adminorderHistory")
	public String adminOrderHistory(Model model) {
		List<PurchaseManage> order = adminService.SelectOrderAll();
		model.addAttribute("order",order);		
		return "admin/order-history-admin";
	}

	//注文画面へ
	@GetMapping("/adminorder")
	public String adminOrder() {
		return "admin/order-management";
	}

	//売上管理画面へ
	@GetMapping("/sales")
	public String adminSale(Model model) {
		List<Salemanage> sale = adminService.SelectSaleAll();
		model.addAttribute("sale", sale);
		return "admin/sales";
	}



}
