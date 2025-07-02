package com.gabriel.project.service;

import com.gabriel.project.model.FileDto;
import com.gabriel.project.model.FileEntity;
import com.gabriel.project.repository.ArquivoEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceDownload {
    @Autowired
    private ArquivoEntityRepository repository;

    public FileDto download(String name){
    Optional<FileEntity> entity = repository.findByName(name);
        if (entity.isEmpty()){
            System.out.println("Arquivo não existe");
        }
        if (entity.isPresent()){
            var arquivo = new FileDto(entity.get().getName(),entity.get().getType(),entity.get().getFile());
            return arquivo;
        }
        return null;
    }

}
