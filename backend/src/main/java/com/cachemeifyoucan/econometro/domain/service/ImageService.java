package com.cachemeifyoucan.econometro.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cachemeifyoucan.econometro.domain.model.Image;
import com.cachemeifyoucan.econometro.domain.repository.ImageRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public List<Image> createImages(List<Image> images) {
        return imageRepository.saveAll(images);
    }

    public Image createImage(String content) {
        Image image = new Image(content);
        return imageRepository.save(image);
    }

    public Image getImageById(long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Image not found with id: " + id));
    }

    @Transactional
    public void deleteImage(long id) {
        imageRepository.deleteById(id);
    }

}
