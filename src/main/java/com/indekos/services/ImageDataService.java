package com.indekos.services;

import com.indekos.dto.response.ImageUploadResponse;
import com.indekos.model.ImageData;
import com.indekos.repository.ImageDataRepository;
import com.indekos.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageDataService {
    @Autowired
    private ImageDataRepository imageDataRepository;

    public ImageUploadResponse uploadImage(MultipartFile file) throws IOException {

        //        ImageData image = ImageData.builder()
        //                .name(file.getOriginalFilename())
        //                .type(file.getContentType())
        //                .imageData(ImageUtil.compressImage(file.getBytes())).build();

        ImageData image = new ImageData(10L, file.getOriginalFilename(), file.getContentType(), Utils.compressImage(file.getBytes()));

        imageDataRepository.save(image);

//        return new ImageUploadResponse("Image uploaded successfully: " +
//                file.getOriginalFilename());
        return new ImageUploadResponse(file, "Test image name");

    }

    @Transactional
    public ImageData getInfoByImageByName(String name) {
        Optional<ImageData> dbImage = imageDataRepository.findByName(name);

        return ImageData.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .imageData(Utils.decompressImage(dbImage.get().getImageData())).build();

    }

    @Transactional
    public byte[] getImage(String name) {
        Optional<ImageData> dbImage = imageDataRepository.findByName(name);
        byte[] image = Utils.decompressImage(dbImage.get().getImageData());
        return image;
    }
}
