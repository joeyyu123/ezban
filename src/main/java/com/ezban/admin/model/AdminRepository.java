package com.ezban.admin.model;

import com.ezban.admin.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByAdminAccount(String adminAccount);  
    Optional<Admin> findByAdminName(String adminName);
    Optional<Admin> findByAdminMail(String adminMail);
    Optional<Admin> findByAdminPhone(String adminPhone);
    Optional<Admin> findByAdminAccountAndAdminMail(String account, String email);
    List<Admin> findByAdminAccountContaining(String searchAccount);
}
