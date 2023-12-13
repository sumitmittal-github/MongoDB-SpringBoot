package com.sumit.service;

import com.sumit.collection.Photo;
import com.sumit.repository.PhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public Long addPhoto(MultipartFile image) throws IOException {
        Photo photo = new Photo(
                            image.getOriginalFilename(),
                            new Binary(BsonBinarySubType.BINARY, image.getBytes()));
        return photoRepository.save(photo).getId();
    }

    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id).get();
    }


}