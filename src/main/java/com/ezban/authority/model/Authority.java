package com.ezban.authority.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;

@Entity
@Table(name = "authority")
@IdClass(Authority.AuthorityId.class) 
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_no", referencedColumnName = "admin_no", nullable = false)
    private Admin admin; 
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "func_no", referencedColumnName = "function_no", nullable = false)
    private Function function; 

    
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    
    static class AuthorityId implements Serializable {
        private Integer adminNo;
        private Integer funcNo; 

        public AuthorityId() {}

        public AuthorityId(Integer adminNo, Integer funcNo) {
            this.adminNo = adminNo;
            this.funcNo = funcNo;
        }

        public Integer getAdminNo() {
            return adminNo;
        }

        public void setAdminNo(Integer adminNo) {
            this.adminNo = adminNo;
        }

        public Integer getFuncNo() {
            return funcNo;
        }

        public void setFuncNo(Integer funcNo) {
            this.funcNo = funcNo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AuthorityId that = (AuthorityId) o;
            return Objects.equals(adminNo, that.adminNo) &&
                    Objects.equals(funcNo, that.funcNo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(adminNo, funcNo);
        }
    }

}
