package com.rhaveeval.shoppingcart.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rhaveeval.shoppingcart.dto.ImageDto;
import com.rhaveeval.shoppingcart.exceptions.ResourceNotFoundException;
import com.rhaveeval.shoppingcart.model.Image;
import com.rhaveeval.shoppingcart.model.Product;
import com.rhaveeval.shoppingcart.repository.ImageRepository;
import com.rhaveeval.shoppingcart.service.product.ProductServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements ImageServiceImpl {
	private final ImageRepository imageRepository;
	private final ProductServiceImpl productServiceImpl;

	@Override
	public Image getImageById(Long id) {
		return imageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
	}

	@Override
	public void deleteImageById(Long id) {
		imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
			throw new ResourceNotFoundException("No image found with id: " + id);
		});
	}

	@Override
	public List<ImageDto> saveImages(Long productId, List<MultipartFile> files) {
		Product product = productServiceImpl.getProductById(productId);

		List<ImageDto> savedImageDto = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				image.setProduct(product);

				String buildDownloadUrl = "/api/v1/images/image/download/";
				String downloadUrl = buildDownloadUrl + image.getId();
				image.setDownloadUrl(downloadUrl);
				Image savedImage = imageRepository.save(image);

				savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
				imageRepository.save(savedImage);

				ImageDto imageDto = new ImageDto();
				imageDto.setId(savedImage.getId());
				imageDto.setFileName(savedImage.getFileName());
				imageDto.setDownloadUrl(savedImage.getDownloadUrl());
				savedImageDto.add(imageDto);

			} catch (IOException | SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return savedImageDto;
	}

	@Override
	public void updateImage(MultipartFile file, Long imageId) {
		Image image = getImageById(imageId);
		try {
			image.setFileName(file.getOriginalFilename());
			image.setFileName(file.getOriginalFilename());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepository.save(image);
		} catch (IOException | SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
