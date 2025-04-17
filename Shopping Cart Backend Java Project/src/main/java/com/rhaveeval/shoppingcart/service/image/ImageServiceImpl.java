package com.rhaveeval.shoppingcart.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rhaveeval.shoppingcart.dto.ImageDto;
import com.rhaveeval.shoppingcart.model.Image;

public interface ImageServiceImpl {

	Image getImageById(Long id);

	void deleteImageById(Long id);

	List<ImageDto> saveImages(Long productId, List<MultipartFile> files);

	void updateImage(MultipartFile file, Long imageId);
}
