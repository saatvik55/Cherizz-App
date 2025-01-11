package com.gratitude.gratitude_photodiary.service;

import com.gratitude.gratitude_photodiary.entity.Image;
import com.gratitude.gratitude_photodiary.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(Image image) throws ExecutionException, InterruptedException {
        imageRepository.saveImage(image);
    }

    public List<Image> getImagesByUserId(String userId) throws ExecutionException, InterruptedException {
        return imageRepository.getImagesByUserId(userId);
    }

    public void deleteImage(String imageId) throws ExecutionException, InterruptedException {
        imageRepository.deleteImage(imageId);
    }


}
