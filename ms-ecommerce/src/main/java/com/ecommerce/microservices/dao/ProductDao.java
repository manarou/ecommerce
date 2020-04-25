package com.ecommerce.microservices.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.microservices.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

	public Product findById(int id);

	public List<Product> findByPrixGreaterThan(double prixLimit);

	@Query("SELECT id, name, prix FROM Product p WHERE p.prix > :prixLimit")
	public List<Product> findExpensiveProducts(@Param("prixLimit") Double prix);
}
