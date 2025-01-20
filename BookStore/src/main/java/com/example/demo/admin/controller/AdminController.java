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
//1/20髙田編集

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@PostMapping("/admin/order")
	public String itemOrder(@RequestBody AdminDto adminDto,Model model) {
		adminService.AddToOrder(adminDto);
		List<PurchaseManage> purchase = adminService.SelectOrderAll();
		model.addAttribute("order", purchase);
		return "redirect/:admin/order-managemen";
	}
	

	//トップページへ
	@GetMapping("/adminprivate/admin-top")
	public String adminTop() {
		return "admin/adminprivate/admin-top";
	}
	
	@PostMapping("/adminprivate/admin-top")
	public String adminTop2() {
		return "admin/adminprivate/admin-top";
	}


	//ログインページへ
	@GetMapping("/adminlogin/admin-login")
	public String adminLogin() {
		return "admin/adminlogin/admin-login";
	}
	
	@PostMapping("/adminlogin/admin-login")
	public String adminLogin2() {
		return "admin/adminlogin/admin-login";
	}

	//在庫画面へ
	@GetMapping("/admin/inventory")
	public String adminInventory(Model model) {
		List<Item> item = adminService.SelectItemAll();
		model.addAttribute("item", item);
		return "admin/inventory";
	}

	//注文履歴へ
	@GetMapping("/admin/adminorderHistory")
	public String adminOrderHistory(Model model) {
		List<PurchaseManage> order = adminService.SelectOrderAll();
		model.addAttribute("order",order);		
		return "admin/order-history-admin";
	}

	//注文画面へ
	@GetMapping("/admin/adminorder")
	public String adminOrder() {
		return "admin/order-management";
	}

//	//売上管理画面へ
	@GetMapping("/admin/sales")
	public String adminSale(Model model) {
		List<Salemanage> sale = adminService.SelectSaleAll();
		model.addAttribute("sale", sale);
		return "admin/sales";
	}



}
