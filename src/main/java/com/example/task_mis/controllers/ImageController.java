package com.example.task_mis.controllers;
import com.example.task_mis.entities.Image;
import com.example.task_mis.services.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/{userId}/upload")
    public Image uploadImage(@PathVariable Long userId,
                             @RequestParam("name") String name,
                             @RequestParam("imageName") MultipartFile file) throws IOException {
        return imageService.saveImage(userId, name, file);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable Long imageId) {
        Resource imageFile = imageService.getImageFile(imageId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageFile);
    }

    @DeleteMapping({"/{imageId}/delete"})
    public void deletePost(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
    }








}
