package com.sumit.controller;

import com.sumit.collection.Photo;
import com.sumit.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/add")
    public ResponseEntity<Long> addPhoto(@RequestParam("image") MultipartFile image) throws IOException {
        Long photoId = photoService.addPhoto(image);
        return new ResponseEntity<>(photoId, HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable Long id){
        Photo photo = photoService.getPhotoById(id);
        Resource resource = new ByteArrayResource(photo.getPhoto().getData());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+photo.getTitle()+"\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}