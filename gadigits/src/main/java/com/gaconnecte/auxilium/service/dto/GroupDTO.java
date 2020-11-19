package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.gaconnecte.auxilium.domain.User;

public class GroupDTO implements Serializable{

	private Long id;
	private Long idClient;
	private String name;
	private Long idProduct;
	private String productName;
	//private Set<User> users = new HashSet<>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getIdClient() {
		return idClient;
	}
	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}
	public Long getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
