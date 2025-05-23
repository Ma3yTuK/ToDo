package com.todo.todo_back.services;

import com.todo.todo_back.entities.Image;
import com.todo.todo_back.repositories.FileSystemRepository;
import com.todo.todo_back.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FileLocationService {

    private final FileSystemRepository fileSystemRepository;
    private final ImageRepository imageRepository;

    public Image save(byte[] bytes, String name) throws Exception {
        String location = fileSystemRepository.save(bytes, name);
        Image image = new Image();
        image.setLocation(location);

        return imageRepository.save(image);
    }

    public FileSystemResource find(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return fileSystemRepository.findInFileSystem(image.getLocation());
    }
}
