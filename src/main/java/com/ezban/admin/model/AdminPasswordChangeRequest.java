package com.ezban.admin.model; 

public class AdminPasswordChangeRequest {
    private String account;
    private String oldPassword;
    private String newPassword;


    public AdminPasswordChangeRequest() {
    }

    
    public String admingetAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
