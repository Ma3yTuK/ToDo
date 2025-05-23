package com.todo.todo_back.web_controllers.file_system_controller;

import com.todo.todo_back.entities.Image;
import com.todo.todo_back.services.FileLocationService;
import com.todo.todo_back.web_controllers.review_controller.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("file-system")
class FileSystemController {

    private final FileLocationService fileLocationService;

    @PostMapping("/image")
    public Image uploadImage(@RequestParam MultipartFile image) throws Exception {
        try {
            return fileLocationService.save(image.getBytes(), image.getOriginalFilename());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/image/{imageId}")
    public FileSystemResource downloadImage(@PathVariable Long imageId) {
        return fileLocationService.find(imageId);
    }
}
