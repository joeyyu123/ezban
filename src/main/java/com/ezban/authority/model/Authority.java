package com.ezban.authority.model;

import com.ezban.admin.model.Admin;
import com.ezban.func.model.Func;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "authority")
public class Authority implements Serializable {
    @Id
    @Column(name = "authority_no", nullable = false)
    private Integer authorityNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_no", nullable = false)
    private Admin admin;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "func_no",nullable = false)
    private Func func;

    public Integer getAuthorityNo() {
        return authorityNo;
    }

    public void setAuthorityNo(Integer authorityNo) {
        this.authorityNo = authorityNo;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

}