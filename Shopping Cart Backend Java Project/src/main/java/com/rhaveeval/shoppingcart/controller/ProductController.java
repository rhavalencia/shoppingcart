package com.rhaveeval.shoppingcart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhaveeval.shoppingcart.dto.ProductDto;
import com.rhaveeval.shoppingcart.exceptions.ResourceNotFoundException;
import com.rhaveeval.shoppingcart.model.Product;
import com.rhaveeval.shoppingcart.request.AddProductRequest;
import com.rhaveeval.shoppingcart.request.UpdateProductRequest;
import com.rhaveeval.shoppingcart.response.ApiResponse;
import com.rhaveeval.shoppingcart.service.product.ProductServiceImpl;

import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

	private final ProductServiceImpl productServiceImpl;

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts() {
		List<Product> products = productServiceImpl.getAllProducts();
		List<ProductDto> convertedProducts = productServiceImpl.getConvertedProducts(products);
		return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));

	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
		try {
			Product product = productServiceImpl.getProductById(id);
			ProductDto productDto = productServiceImpl.convertToDto(product);
			return ResponseEntity.ok(new ApiResponse("Success", productDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}

	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
		try {
			Product newProduct = productServiceImpl.addProduct(product);
			ProductDto productDto = productServiceImpl.convertToDto(newProduct);
			return ResponseEntity.ok(new ApiResponse("New product added", productDto));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/product/{id}/update")
	public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long id) {
		try {
			Product product = productServiceImpl.updateProduct(request, id);
			ProductDto productDto = productServiceImpl.convertToDto(product);
			return ResponseEntity.ok(new ApiResponse("Product Updated Successfully", productDto));
		} catch (Exception e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/product/{id}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
		try {
			productServiceImpl.deleteProduct(id);
			return ResponseEntity.ok(new ApiResponse("Product Deleted Successfully", id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/by/brand-and-name")
	public ResponseEntity<ApiResponse> getProductByBrancdAndName(@RequestParam String brandName,
			@RequestParam String productName) {
		try {
			List<Product> products = productServiceImpl.getProductsByBrandAndName(brandName, productName);
			if (products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
			}
			List<ProductDto> convertedProducts = productServiceImpl.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/by/category-and-brand")
	public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,
			@RequestParam String brand) {
		try {
			List<Product> products = productServiceImpl.getProductsByCategoryAndBrand(category, brand);
			if (products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
			}
			List<ProductDto> convertedProducts = productServiceImpl.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
		}

	}

	@GetMapping("/{name}/products")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
		try {
			List<Product> products = productServiceImpl.getProductsByName(name);
			if (products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
			}
			List<ProductDto> convertedProducts = productServiceImpl.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
		}
	}

	@GetMapping("/product/by-brand")
	public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {
		try {
			List<Product> products = productServiceImpl.getProductsByBrand(brand);
			if (products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
			}
			List<ProductDto> convertedProducts = productServiceImpl.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/{category}/all/products")
	public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
		try {
			List<Product> products = productServiceImpl.getProductsByCategory(category);
			if (products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
			}
			List<ProductDto> convertedProducts = productServiceImpl.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/product/count/by-brand/and-name")
	public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand,
			@RequestParam String name) {
		try {
			var productCount = productServiceImpl.countProductsByBrandAndName(brand, name);
			return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

}
