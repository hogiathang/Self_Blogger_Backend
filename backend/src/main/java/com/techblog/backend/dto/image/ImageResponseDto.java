package com.techblog.backend.dto.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDto {
    private String imageName;
    private String imageUrl;
    private String contentType;
    private Long size;
}
