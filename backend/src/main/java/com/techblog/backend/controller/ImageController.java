package com.techblog.backend.controller;

import com.techblog.backend.dto.image.ImageResponseDto;
import com.techblog.backend.service.publicInterface.file.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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
    @PostMapping("")
    @Operation(summary = "Tải lên hình ảnh",
            description = "Phương thức này cho phép người dùng tải lên hình ảnh và lưu trữ chúng.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hình ảnh đã được tải lên thành công"),
            @ApiResponse(responseCode = "400", description = "Yêu cầu không hợp lệ hoặc hình ảnh không được cung cấp"),
            @ApiResponse(responseCode = "500", description = "Lỗi máy chủ khi xử lý hình ảnh")
    })
    public ResponseEntity<ImageResponseDto> uploadImage(@RequestParam("image")MultipartFile image, HttpServletRequest request) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ImageResponseDto responseUrl =  imageService.saveImage(authentication.getName().toLowerCase(), image.getBytes(), image.getOriginalFilename(), image.getContentType(), request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseUrl);
    }

    /**
     * Phương thức này xử lý yêu cầu lấy hình ảnh theo ID.
     * @return ResponseEntity<String> - Trả về URL của hình ảnh
     */
    @GetMapping("/{username}/{imageName}")
    @Operation(summary = "Lấy hình ảnh theo ID",
            description = "Phương thức này cho phép người dùng lấy hình ảnh đã tải lên trước đó.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hình ảnh đã được lấy thành công"),
            @ApiResponse(responseCode = "404", description = "Hình ảnh không tồn tại hoặc không tìm thấy"),
            @ApiResponse(responseCode = "500", description = "Lỗi máy chủ khi lấy hình ảnh")
    })
    public ResponseEntity<Void> getImageById(@PathVariable String username, @PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream image = imageService.getImage(username.toLowerCase(), imageName);

        if (image == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            response.setContentType("image/jpeg");
            response.setHeader("Content-Disposition", "inline; filename=\"" + imageName + "\"");
            response.getOutputStream().write(image.readAllBytes());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            return null;
        }
    }

    /**
     * Phương thức này xử lý yêu cầu xóa hình ảnh theo ID.
     * @return ResponseEntity<String> - Trả về thông báo thành công hoặc thất bại
     */
    @DeleteMapping("/{imageName}")
    @Operation(summary = "Xóa hình ảnh theo ID",
            description = "Phương thức này cho phép người dùng xóa hình ảnh đã tải lên trước đó.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Hình ảnh đã được xóa thành công"),
            @ApiResponse(responseCode = "404", description = "Hình ảnh không tồn tại hoặc không tìm thấy"),
            @ApiResponse(responseCode = "500", description = "Lỗi máy chủ khi xóa hình ảnh")
    })
    public ResponseEntity<String> deleteImageById(@PathVariable String imageName) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String responseMessage = imageService.deleteImage(authentication.getName().toLowerCase(), imageName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseMessage);
    }
}
