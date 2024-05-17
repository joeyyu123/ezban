package com.ezban.admin.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminrepository;
	
	public List<Admin> getAllAdmins(){
		return adminrepository.findAll();
	}
	
	public Admin getAdminById(Integer adminNo) {
		return adminrepository.getById(adminNo);
	}
	
	public Admin getAdminByAdminAccount(String adminAccount) {
		Optional<Admin> optionalAdmin = adminrepository.findByAdminAccount(adminAccount);
		return optionalAdmin.orElse(null);
	}
	
	public Admin getAdminByAdminName(String adminName) {
		Optional<Admin> optionalAdmin = adminrepository.findByAdminName(adminName);
		return optionalAdmin.orElse(null);
	}
	
	public Admin getAdminByAdminMail(String adminMail) {
		Optional<Admin> optionalAdmin = adminrepository.findByAdminMail(adminMail);
		return optionalAdmin.orElse(null);
	}
	
	public List<Admin> getAdminByStatus(Byte adminStatus){
		
		return adminrepository.findByAdminStatus(adminStatus);
	}

}
