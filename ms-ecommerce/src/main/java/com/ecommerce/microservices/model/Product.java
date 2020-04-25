package com.ecommerce.microservices.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(value = {"prixDAchat", "id"})

@Entity
public class Product {
	@Id
	private Integer id;
	@GeneratedValue
	private String name;
	private Double prix;
	private Double prixDAchat;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}
	
	public Double getPrixDAchat() {
		return prixDAchat;
	}
	
	public void setPrixDAchat(Double prixDAchat) {
		this.prixDAchat = prixDAchat;
	}
	

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", prix=" + prix + ", prixDAchat=" + prixDAchat + "]";
	}

}
