package com.indekos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ImageUploadResponse {
    private MultipartFile image;
    private String imageName;
}
