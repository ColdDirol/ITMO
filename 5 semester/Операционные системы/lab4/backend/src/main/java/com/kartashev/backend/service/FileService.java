package com.kartashev.backend.service;

import com.kartashev.backend.model.FileEntity;
import com.kartashev.backend.repository.FileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public FileEntity createFileOrDirectory(String name, Long parentId, boolean isDir, String content) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(name);
        fileEntity.setParentId(parentId);
        fileEntity.setIsDir(isDir);
        fileEntity.setContent(isDir ? null : content);
        fileEntity.setCreatedAt(LocalDateTime.now());
        fileEntity.setUpdatedAt(LocalDateTime.now());

        return fileRepository.save(fileEntity);
    }

    public FileEntity uploadFile(String name, Long parentId, MultipartFile file) {
        try {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setName(name);
            fileEntity.setParentId(parentId);
            fileEntity.setIsDir(false);
            fileEntity.setContent(new String(file.getBytes()));
            fileEntity.setCreatedAt(LocalDateTime.now());
            fileEntity.setUpdatedAt(LocalDateTime.now());

            return fileRepository.save(fileEntity);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    public List<FileEntity> listFiles(Long parentId) {
        return parentId == null
                ? fileRepository.findByParentIdIsNull()
                : fileRepository.findByParentId(parentId);
    }

    public void deleteFile(Long id) {
        FileEntity fileEntity = fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("File not found with ID: " + id));
        fileRepository.delete(fileEntity);
    }
}
