package com.rhaveeval.shoppingcart.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rhaveeval.shoppingcart.dto.ImageDto;
import com.rhaveeval.shoppingcart.exceptions.ResourceNotFoundException;
import com.rhaveeval.shoppingcart.model.Image;
import com.rhaveeval.shoppingcart.response.ApiResponse;
import com.rhaveeval.shoppingcart.service.image.ImageServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
	private final ImageServiceImpl imageServiceImpl;

	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files,
			@RequestParam Long productId) {
		try {
			List<ImageDto> imageDtos = imageServiceImpl.saveImages(productId, files);

			return ResponseEntity.ok(new ApiResponse("Upload Success", imageDtos));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed", e.getMessage()));
		}

	}

	@GetMapping("/image/download/{imageId}")
	public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
		Image image = imageServiceImpl.getImageById(imageId);
		ByteArrayResource resource = new ByteArrayResource(
				image.getImage().getBytes(1, (int) image.getImage().length()));
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
				.body(resource);
	}

	@PutMapping("/image/{imageId}/update")
	public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
		try {
			Image image = imageServiceImpl.getImageById(imageId);
			if (image != null) {
				imageServiceImpl.updateImage(file, imageId);
				return ResponseEntity.ok(new ApiResponse("Update success!", null));
			}
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		return ResponseEntity.status(INTERNAL_SERVER_ERROR)
				.body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));
	}

	@DeleteMapping("/image/{imageId}/delete")
	public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
		try {
			Image image = imageServiceImpl.getImageById(imageId);
			if (image != null) {
				imageServiceImpl.deleteImageById(imageId);
				return ResponseEntity.ok(new ApiResponse("Delete success!", null));
			}
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		return ResponseEntity.status(INTERNAL_SERVER_ERROR)
				.body(new ApiResponse("Delete failed!", INTERNAL_SERVER_ERROR));
	}

}
