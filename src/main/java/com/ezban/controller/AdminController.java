package com.ezban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ezban.admin.model.Admin;
import com.ezban.admin.model.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminservice;
	
	@GetMapping
	public List<Admin> getAllAdmins(){
		
		return adminservice.getAllAdmins();
	}
	
	@GetMapping("/{adminNo}")
	public Admin getAdminById(@PathVariable Integer adminNo) {
		
		return adminservice.getAdminById(adminNo);
	}
	
	public Admin getAdminByAdminAccount(@PathVariable String adminAccount) {
		
		return adminservice.getAdminByAdminAccount(adminAccount);
	}
	
	public Admin getAdminByAdminName(@PathVariable String adminName) {
		
		return adminservice.getAdminByAdminName(adminName);
	}
	
	public Admin getAdminByAdminMail(@PathVariable String adminMail) {
		
		return adminservice.getAdminByAdminMail(adminMail);
	}
	
	public List<Admin> getAdminByStatus(@PathVariable Byte adminStatus){
		
		return adminservice.getAdminByStatus(adminStatus);
	}

}
