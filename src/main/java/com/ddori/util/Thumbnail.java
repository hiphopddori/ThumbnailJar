package com.ddori.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Thumbnail implements ThumbnailInterface {

    private final Path rootLocation;
    public Thumbnail (Path rootLocation) {
        this.rootLocation = rootLocation;
    }
    public void make() {
        System.out.println("TEST");
    }

    @Override
    public void make(MultipartFile file) throws Exception {

        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file.");
            }
            final String originalFilename = file.getOriginalFilename();
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(originalFilename))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new Exception(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                String orgFilePath = destinationFile.toFile().getAbsolutePath();
                String thumbFilePath = (this.rootLocation.resolve("th_" + originalFilename)).toFile().getAbsolutePath();
                Thumbnails.of(orgFilePath).size(100,100).toFile(thumbFilePath);
            }
        }
        catch (IOException e) {
            throw new Exception("Failed to store file.", e);
        }
    }

}
