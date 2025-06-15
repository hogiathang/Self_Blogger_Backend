package com.techblog.backend.controller;

import com.techblog.backend.service.publicInterface.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * ImageController có trách nhiệm xử lý các yêu cầu liên quan đến hình ảnh.
 */
@RestController(value = "imageController")
@RequestMapping("/api/v1/images")
@Tag(name="Image", description = "Api quản lý hình ảnh")
@CrossOrigin(origins = "localhost:3000", maxAge = 3600)
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Phương thức này xử lý yêu cầu tải lên hình ảnh.
     * @return ResponseEntity<String> - Trả về URL cuả ảnh đã tải lên
     */
    @PostMapping("/upload")
    @Operation(summary = "Tải lên hình ảnh",
            description = "Phương thức này cho phép người dùng tải lên hình ảnh và lưu trữ chúng.")
    @ApiResponse(responseCode = "201", description = "Hình ảnh đã được tải lên thành công")
    public ResponseEntity<String> uploadImage(@RequestParam("image")MultipartFile image) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String responseUrl =  imageService.saveImage(authentication.getName().toLowerCase(), image.getBytes(), image.getOriginalFilename());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseUrl);
    }

    /**
     * Phương thức này xử lý yêu cầu lấy hình ảnh theo ID.
     * @return ResponseEntity<String> - Trả về URL của hình ảnh
     */
    @GetMapping("/get/{imageName}")
    @Operation(summary = "Lấy hình ảnh theo ID",
            description = "Phương thức này cho phép người dùng lấy hình ảnh đã tải lên trước đó.")
    @ApiResponse(responseCode = "200", description = "Hình ảnh đã được lấy thành công")
    public ResponseEntity<String> getImageById(@PathVariable String imageName) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String imageUrl = imageService.getImage(username.toLowerCase(), imageName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageUrl);
    }

    /**
     * Phương thức này xử lý yêu cầu xóa hình ảnh theo ID.
     * @return ResponseEntity<String> - Trả về thông báo thành công hoặc thất bại
     */
    @PutMapping("/delete/{imageName}")
    @Operation(summary = "Xóa hình ảnh theo ID",
            description = "Phương thức này cho phép người dùng xóa hình ảnh đã tải lên trước đó.")
    @ApiResponse(responseCode = "200", description = "Hình ảnh đã được xóa thành công")
    public ResponseEntity<String> deleteImageById(@PathVariable String imageName) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String responseMessage = imageService.deleteImage(authentication.getName().toLowerCase(), imageName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseMessage);
    }
}
