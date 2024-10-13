package com.dailycodework.dream_shops.controller;

import static org.springframework.http.HttpStatus.*;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.dream_shops.dto.ImageDto;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Image;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.image.IImageService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/image")
public class ImageController {

	private final IImageService imageService;
	
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
		try {
			List<ImageDto> imageDtos = imageService.saveImages(files, productId);
			return ResponseEntity.ok(new ApiResponse("Upload success!",imageDtos));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed",e.getMessage()));
		}
	}
	
	@GetMapping("/download/{imageId}")
	public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException{
		Image image = imageService.getImageById(imageId);
		ByteArrayResource resource = new ByteArrayResource(image.getImageBlob().getBytes(1, (int) image.getImageBlob().length()));
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileType() +"\"")
				.body(resource);
	}
	
	@PutMapping("/{imageId}")
	public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
		try {
			Image image = imageService.getImageById(imageId);
			if(image != null) {
				imageService.updateImage(file, imageId);
				return ResponseEntity.ok(new ApiResponse("Update Success", null));
			}
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed", INTERNAL_SERVER_ERROR));
	}
	
	@DeleteMapping("/{imageId}")
	public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
		try {
			Image image = imageService.getImageById(imageId);
			if(image != null) {
				imageService.deleteImageById(imageId);
				return ResponseEntity.ok(new ApiResponse("Delete Success", null));
			}
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete Failed", INTERNAL_SERVER_ERROR));
	}
}
