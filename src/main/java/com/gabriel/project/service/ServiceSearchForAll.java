package com.gabriel.project.service;

import com.gabriel.project.model.FileDto;
import com.gabriel.project.repository.ArquivoEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceSearchForAll {
    @Autowired
    ArquivoEntityRepository repository;

    public List<FileDto> getall(){
        List<FileDto> files = repository.findAll().stream().map(file -> new FileDto(file.getName(), file.getType())).toList();
    return files;
    }
}
