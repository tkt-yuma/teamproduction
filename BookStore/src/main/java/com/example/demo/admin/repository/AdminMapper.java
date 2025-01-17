package com.example.demo.admin.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.admin.dto.AdminDto;
import com.example.demo.admin.entity.Admin;
import com.example.demo.admin.entity.Item;
import com.example.demo.admin.entity.PurchaseManage;
import com.example.demo.admin.entity.Salemanage;

@Mapper
public interface AdminMapper {
	
	Admin findById(int adminid);

	void AddOrder(AdminDto adminDto);
	
	List<Salemanage> SelectSaleAll();
	
	List<PurchaseManage> SelectOrderAll();

	List<Item> SelectItemAll();
	
	
	
}
