package com.kartashev.backend.repository;

import com.kartashev.backend.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findByParentId(Long parentId);

    List<FileEntity> findByParentIdIsNull();
}
