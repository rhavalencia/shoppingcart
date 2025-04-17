package com.rhaveeval.shoppingcart.service.product;

import java.util.List;

import com.rhaveeval.shoppingcart.dto.ProductDto;
import com.rhaveeval.shoppingcart.model.Product;
import com.rhaveeval.shoppingcart.request.AddProductRequest;
import com.rhaveeval.shoppingcart.request.UpdateProductRequest;

public interface ProductServiceImpl {
	List<Product> getAllProducts();

	List<Product> getProductsByCategory(String category);

	List<Product> getProductsByBrand(String brand);

	List<Product> getProductsByCategoryAndBrand(String category, String brand);

	List<Product> getProductsByName(String name);

	List<Product> getProductsByBrandAndName(String brand, String name);
	
	Long countProductsByBrandAndName(String brand, String name);
	
	Product addProduct(AddProductRequest request);

	Product getProductById(Long id);

	Product updateProduct(UpdateProductRequest request, Long id);

	void deleteProduct(Long id);
	
	List<ProductDto> getConvertedProducts(List<Product> products);
	
	ProductDto convertToDto(Product product);
}
