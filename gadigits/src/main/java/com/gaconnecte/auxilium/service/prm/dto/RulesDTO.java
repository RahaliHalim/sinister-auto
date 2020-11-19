package com.gaconnecte.auxilium.service.prm.dto;

import java.io.Serializable;

public class RulesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer ruleNumber;
    private String code;
    private String label;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getRuleNumber() {
		return ruleNumber;
	}
	public void setRuleNumber(Integer ruleNumber) {
		this.ruleNumber = ruleNumber;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
    
    

}