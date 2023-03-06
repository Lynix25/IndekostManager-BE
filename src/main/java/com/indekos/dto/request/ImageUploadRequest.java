package com.indekos.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImageUploadRequest extends AuditableRequest {
    private MultipartFile image;
}
