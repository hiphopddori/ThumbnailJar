package com.ddori.util;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

public interface ThumbnailInterface {
    void make() throws Exception;
    void make(MultipartFile file) throws Exception;
}
