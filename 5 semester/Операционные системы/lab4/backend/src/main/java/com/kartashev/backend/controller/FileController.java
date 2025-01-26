package com.kartashev.backend.controller;

import com.kartashev.backend.model.FileEntity;
import com.kartashev.backend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService service;

    @PostMapping("/create")
    public ResponseEntity<FileEntity> createFileOrDirectory(
            @RequestParam String name,
            @RequestParam(required = false) Long parentId,
            @RequestParam boolean isDir,
            @RequestParam(required = false) String content) {
        FileEntity fileEntity = service.createFileOrDirectory(name, parentId, isDir, content);
        return ResponseEntity.ok(fileEntity);
    }

    // Загрузка файла
    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(
            @RequestParam String name,
            @RequestParam(required = false) Long parentId,
            @RequestParam MultipartFile file) {
        FileEntity fileEntity = service.uploadFile(name, parentId, file);
        return ResponseEntity.ok(fileEntity);
    }

    // Получение списка файлов в директории
    @GetMapping("/list")
    public ResponseEntity<List<FileEntity>> listFiles(@RequestParam(required = false) Long parentId) {
        List<FileEntity> files = service.listFiles(parentId);
        return ResponseEntity.ok(files);
    }

    // Удаление файла или директории
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFile(@RequestParam Long id) {
        service.deleteFile(id);
        return ResponseEntity.noContent().build();
    }
}
