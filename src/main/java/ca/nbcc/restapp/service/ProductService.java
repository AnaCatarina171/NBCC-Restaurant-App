package ca.nbcc.restapp.service;

import ca.nbcc.restapp.repo.ProductJpaRepo;

public class ProductService {
	
	private ProductJpaRepo pRepo;
	
	public ProductService(ProductJpaRepo pRepo) {
		this.pRepo = pRepo;
	}

}
