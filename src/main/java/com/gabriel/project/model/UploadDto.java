package com.gabriel.project.model;

import org.springframework.web.multipart.MultipartFile;

public record UploadDto(MultipartFile file, String name_file) {
}
