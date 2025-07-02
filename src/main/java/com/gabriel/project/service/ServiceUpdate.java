package com.gabriel.project.service;

import com.gabriel.project.model.FileDto;
import com.gabriel.project.model.FileEntity;
import com.gabriel.project.model.UploadDto;
import com.gabriel.project.repository.ArquivoEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
@Service
public class ServiceUpdate {
    @Autowired
    private ArquivoEntityRepository repository;

    public FileDto update(UploadDto dto, String name) throws IOException {
        Optional<FileEntity> entity = repository.findByName(name);
        FileEntity file = entity.get();
        if (entity.isEmpty()){
            System.out.println("Arquivo não existe");
        }
        if (entity.isPresent()){
            if(dto.name_file() != null) {
                file.setName(dto.name_file());
            }
            if(dto.file() != null){
                file.setFile(dto.file().getBytes());
                file.setType(dto.file().getContentType());
            }
            repository.save(file);
            var arquivo = new FileDto(file.getName(),file.getType());
            return arquivo;
        }
        return null;
    }
}
