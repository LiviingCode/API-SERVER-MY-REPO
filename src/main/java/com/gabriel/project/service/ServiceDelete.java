package com.gabriel.project.service;

import com.gabriel.project.model.FileDto;
import com.gabriel.project.model.FileEntity;
import com.gabriel.project.repository.ArquivoEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ServiceDelete {
    @Autowired
    private ArquivoEntityRepository repository;

    public FileDto delete(String name){
        Optional<FileEntity> entity = repository.findByName(name);
        if (entity.isEmpty()){
            System.out.println("Arquivo não existe");
        }
        if (entity.isPresent()){
            repository.deleteByName(name);
            var arquivo = new FileDto(entity.get().getName(),entity.get().getType());
            return arquivo;
        }
        return null;
    }
}
