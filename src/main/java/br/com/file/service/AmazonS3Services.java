package br.com.file.service;

import org.springframework.core.io.ByteArrayResource;

public interface AmazonS3Services {
    ByteArrayResource downloadFile(String keyName);

    String uploadFile(byte[] content, String contentType);

    void deleteFile(String keyName);
}
