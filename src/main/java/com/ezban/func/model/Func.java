package com.ezban.func.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	
	@Entity
	@Table(name = "func", schema = "ezban")
	public class Func {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	    @Column(name = "func_no", nullable = false)
	    private Integer funcNo;  

	    @Column(name = "func_name", nullable = false, length = 20)
	    private String funcName;  

	    public Func() {}

	   
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
