package com.gabriel.project.repository;

import com.gabriel.project.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArquivoEntityRepository extends JpaRepository<FileEntity,Long> {

    Optional<FileEntity> findByName(String name);
    void deleteByName(String name);
}
