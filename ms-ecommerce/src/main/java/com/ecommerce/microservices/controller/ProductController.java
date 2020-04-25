package com.ecommerce.microservices.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.microservices.dao.ProductDao;
import com.ecommerce.microservices.exception.NotFoundProductException;
import com.ecommerce.microservices.model.Product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api( description="API pour es opérations CRUD sur les produits.")
@RestController
public class ProductController {

	@Autowired
	private ProductDao dao;

	@GetMapping(value = "/products")
	public ResponseEntity<List<Product>> productList() {
		List<Product> products = dao.findAll();
		return new ResponseEntity<List<Product>>(products, HttpStatus.FOUND);
	}

	// Récupérer la liste des produits filtrée
//    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
//    public MappingJacksonValue listeProduits() {
//        Iterable<Product> produits = dao.findAll();
//
//        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
//
//        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
//
//        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
//
//        produitsFiltres.setFilters(listDeNosFiltres);
//
//        return produitsFiltres;
//    }

    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id) {
		Product foundProduct = dao.findById(id);
		if (foundProduct != null) {
			return new ResponseEntity<Product>(foundProduct, HttpStatus.FOUND);
		}
		
		throw new NotFoundProductException("Cannot find product with id "+ id);
		//return new ResponseEntity<Product>(foundProduct, HttpStatus.NOT_FOUND);
	}
	
	

	@GetMapping(value = "/products/expensive/{prixLimit}")
	public ResponseEntity<List<Product>> getExpensiveProducts(@PathVariable double prixLimit) {
		List<Product> foundProducts = dao.findByPrixGreaterThan(prixLimit);
		if (foundProducts != null) {
			return new ResponseEntity<List<Product>>(foundProducts, HttpStatus.FOUND);
		}
		return new ResponseEntity<List<Product>>(new ArrayList<Product>(), HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/test/products/expensive/{prixLimit}")
	public ResponseEntity<List<Product>> getExpensiveProducts2(@PathVariable Double prixLimit) {
		List<Product> foundProducts = dao.findExpensiveProducts(prixLimit);
		if (foundProducts != null) {
			return new ResponseEntity<List<Product>>(foundProducts, HttpStatus.FOUND);
		}
		return new ResponseEntity<List<Product>>(new ArrayList<Product>(), HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/products")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		Product productAdded = dao.save(product);

		if (productAdded == null)
			return ResponseEntity.noContent().build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productAdded.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable Integer id) {
		dao.deleteById(id);
		return new ResponseEntity<Product>(HttpStatus.OK);
	}

	@PutMapping(value = "/products")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		Product updatedProduct = dao.save(product);
		if(updatedProduct != null) {
			return new ResponseEntity<Product>(HttpStatus.OK); 
		}
		
		return new ResponseEntity<Product>(HttpStatus.NOT_MODIFIED);
	}
	
	
}
