package com.ezban.func.model;

import com.ezban.authority.model.Authority;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;


@Entity
@Table(name = "func")
public class Func implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "func_no", nullable = false)
    private Integer funcNo;

    @Column(name = "func_name", nullable = false, length = 20)
    private String funcName;

    @OneToMany(mappedBy = "func")
    private Set<Authority> authorities = new LinkedHashSet<>();

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Func() {
    }


    public Func(Integer funcNo, String funcName) {
        this.funcNo = funcNo;
        this.funcName = funcName;
    }


    public Integer getFuncNo() {
        return funcNo;
    }


    public void setFuncNo(Integer funcNo) {
        this.funcNo = funcNo;
    }


    public String getFuncName() {
        return funcName;
    }


    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    @Override
    public String toString() {
        return "FuncVo{" +
                "funcNo=" + funcNo +
                ", funcName='" + funcName + '\'' +
                '}';
    }

}
