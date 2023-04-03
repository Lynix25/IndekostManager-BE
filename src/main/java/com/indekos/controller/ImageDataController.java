package com.indekos.controller;

import com.indekos.dto.response.ImageGetResponse;
import com.indekos.model.ImageData;
import com.indekos.services.ImageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageDataController {
    
	@Autowired
    private ImageDataService imageDataService;

    @PostMapping("/{testId}")
    public ResponseEntity<?> uploadImage(@PathVariable String testId, @RequestBody MultipartFile image) throws IOException {
//        ImageUploadResponse response = imageDataService.uploadImage(image);
    	imageDataService.uploadImage(image);
        System.out.println(testId);
        return ResponseEntity.status(HttpStatus.OK).body("asdsad");
    }

    @GetMapping("/info/{name}")
    public ResponseEntity<?> getImageInfoByName(@PathVariable("name") String name){
        ImageData image = imageDataService.getInfoByImageByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(image);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getImageByName(@PathVariable("name") String name){
        byte[] image = imageDataService.getImage(name);
        ImageGetResponse response = new ImageGetResponse();
        response.setImage(image);
        response.setName("Test Image");
        return new ResponseEntity<>(response, HttpStatus.OK);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(image);
    }
}
