package com.gabriel.project.service;

import com.gabriel.project.model.FileDto;
import com.gabriel.project.model.FileEntity;
import com.gabriel.project.model.UploadDto;
import com.gabriel.project.repository.ArquivoEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ServiceUpload {
    @Autowired
    private ArquivoEntityRepository repository;

    public FileDto salvar(UploadDto dto) throws IOException {
        var arquivo = new FileDto(dto.name_file(),dto.file().getContentType(),dto.file().getBytes());
        var imagemParaSalvar =  new FileEntity(arquivo.name(),arquivo.type(),arquivo.file());
        repository.save(imagemParaSalvar);
        return arquivo;
    }

}
