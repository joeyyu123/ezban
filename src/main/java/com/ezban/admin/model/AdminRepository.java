package com.ezban.admin.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	Optional<Admin> findByAdminAccount(String adminAccount);
	
	Optional<Admin> findByAdminName(String adminName);
	
	Optional<Admin> findByAdminMail(String adminMail);
	
	List<Admin> findByAdminStatus(Byte adminStatus);

}
