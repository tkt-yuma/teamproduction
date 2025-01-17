package com.example.demo.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.admin.dto.AdminDto;
import com.example.demo.admin.entity.Admin;
import com.example.demo.admin.entity.Item;
import com.example.demo.admin.entity.PurchaseManage;
import com.example.demo.admin.entity.Salemanage;
import com.example.demo.admin.repository.AdminMapper;

@Service
public class AdminService {
	@Autowired
	private  AdminMapper adminMapper;
	
	public void AddToOrder(AdminDto adminDto) {
		// TODO 自動生成されたメソッド・スタブ
		adminMapper.AddOrder(adminDto);
	}
	public List<Salemanage> SelectSaleAll(){
		return adminMapper.SelectSaleAll();
		
	}
	public List<PurchaseManage> SelectOrderAll(){
		return adminMapper.SelectOrderAll();
		
	}
	public Admin findById(int id) {
		return adminMapper.findById(id);
	}
	public List<Item> SelectItemAll() {
		
		return adminMapper.SelectItemAll();
	}
	

}
