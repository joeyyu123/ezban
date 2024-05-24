package com.ezban.admin.model;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezban.host.model.HostMailService;
import com.ezban.host.model.HostPassRandom;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private HostMailService hostMailService;

    private static final int PASSWORD_LENGTH = 10;

    @Transactional(rollbackFor = Exception.class)
    public Admin registerAdmin(String adminAccount, String adminName, String adminMail, String adminPhone) {
        // Check unique constraints
        if (adminRepository.findByAdminAccount(adminAccount).isPresent()) {
            throw new DataIntegrityViolationException("帳號已有人使用");
        }
        if (adminRepository.findByAdminMail(adminMail).isPresent()) {
            throw new DataIntegrityViolationException("信箱已有人使用");
        }
        if (adminRepository.findByAdminName(adminName).isPresent()) {
            throw new DataIntegrityViolationException("名稱已有人使用");
        }
        if (adminRepository.findByAdminPhone(adminPhone).isPresent()) {
            throw new DataIntegrityViolationException("電話已有人使用");
        }

        Admin admin = new Admin();
        admin.setAdminAccount(adminAccount);
        admin.setAdminName(adminName);
        admin.setAdminMail(adminMail);
        admin.setAdminPhone(adminPhone);
        admin.setAdminStatus((byte) 1);

        // Generate and set initial password
        String rawPassword = HostPassRandom.generatePassword(PASSWORD_LENGTH);
        admin.setAdminPwd(rawPassword); // 直接使用未加密密碼
        adminRepository.save(admin);

        // Send email with initial password
        String message = "Your initial password is: " + rawPassword + ". Please change it upon your first login for security.";
        hostMailService.sendEmail(admin.getAdminMail(), "Welcome to Ezban", message);

        return admin;
    }

    @Transactional(rollbackFor = Exception.class)
    public void setupAdminPassword(int adminNo) {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminNo);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            String rawPassword = HostPassRandom.generatePassword(PASSWORD_LENGTH);
            admin.setAdminPwd(rawPassword); // 直接使用未加密密碼
            adminRepository.save(admin);

            hostMailService.sendEmail(admin.getAdminMail(), "Welcome to Ezban",
                    "Your initial password is: " + rawPassword + ". Please change it upon your first login for security.");
        } else {
            throw new RuntimeException("Admin not found with ID: " + adminNo);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateAdminPassword(int adminNo, String currentPassword, String rawNewPassword) {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminNo);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            // Verify current password
            if (!currentPassword.equals(admin.getAdminPwd())) {
                throw new RuntimeException("Current password is incorrect");
            }

            admin.setAdminPwd(rawNewPassword); // 直接使用未加密密碼
            adminRepository.save(admin);
            hostMailService.sendEmail(admin.getAdminMail(), "Password Update",
                    "Your password has been successfully updated.");
        } else {
            throw new RuntimeException("No admin found with the specified ID: " + adminNo);
        }
    }

    public boolean authenticate(String adminAccount, String rawPassword) {
        Optional<Admin> optionalAdmin = adminRepository.findByAdminAccount(adminAccount);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            // 直接使用明文比较
            return rawPassword.equals(admin.getAdminPwd());
        } else {
            return false;
        }
    }

    public Admin login(String account, String password) {
        return adminRepository.findByAdminAccount(account)
                .filter(admin -> admin.getAdminPwd().equals(password)) // 普通比較
                .orElse(null);
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        Optional<Admin> adminOptional = adminRepository.findByAdminAccount(username);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (oldPassword.equals(admin.getAdminPwd())) { // 普通比較
                admin.setAdminPwd(newPassword); // 普通比較
                adminRepository.save(admin);
                return true;
            }
        }
        return false;
    }

    public Optional<Admin> findAdminByAccount(String account) {
        return adminRepository.findByAdminAccount(account);
    }

    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public Admin getOneAdmin(Integer adminNo) {
        Optional<Admin> optional = adminRepository.findById(adminNo);
        return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    
    public Optional<Admin> getAdminById(Integer id) {
        return adminRepository.findById(id);
    }
    
    public void disableAdmin(Integer id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            admin.setAdminStatus((byte) 0);
            adminRepository.save(admin);
        } else {
            throw new RuntimeException("Admin with id " + id + " not found");
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void enableAdmin(Integer id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            admin.setAdminStatus((byte) 1); // 啟用
            adminRepository.save(admin);
        } else {
            throw new RuntimeException("Admin with id " + id + " not found");
        }
    }
    
    public List<Admin> searchAdminsByAccount(String searchAccount) {
        return adminRepository.findByAdminAccountContaining(searchAccount);
    }


}
