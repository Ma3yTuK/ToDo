package com.todo.todo_back.repositories;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

@Repository
public class FileSystemRepository {

    String RESOURCES_DIR = Objects.requireNonNull(FileSystemRepository.class.getResource("/"))
            .getPath();

    public String save(byte[] content) throws Exception {
        Path newFile = Paths.get(RESOURCES_DIR + new Date().getTime());
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, content);

        return newFile.toAbsolutePath()
                .toString();
    }

    public FileSystemResource findInFileSystem(String location) {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            // Handle access or file not found problems.
            throw new RuntimeException();
        }
    }
}
