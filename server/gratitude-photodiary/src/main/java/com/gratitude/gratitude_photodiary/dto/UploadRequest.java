package com.gratitude.gratitude_photodiary.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadRequest {
    private String file; // Base64-encoded file or URL
    private String fileName;

}