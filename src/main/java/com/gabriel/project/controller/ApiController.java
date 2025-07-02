package com.gabriel.project.controller;

import com.gabriel.project.model.FileDto;
import com.gabriel.project.model.UploadDto;
import com.gabriel.project.service.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ServiceUpload service;
    @Autowired
    private ServiceDownload serviceDownload;
    @Autowired
    private ServiceDelete serviceDelete;
    @Autowired
    private ServiceUpdate serviceUpdate;
    @Autowired
    private ServiceSearchForAll serviceSearchForAll;

    @GetMapping("/files")
    public ResponseEntity<List<FileDto>> searchForAll(){
        var files = serviceSearchForAll.getall();
        if(files != null){
            return ResponseEntity.status(HttpStatus.OK).body(files);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/download/{name}")
    public ResponseEntity<byte[]> download(@PathVariable @NotNull String name){
        var file = serviceDownload.download(name);

        if(file != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType((file.type())))
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.name() + "\"")
                    .body(file.file());
        }
        else {
            return ResponseEntity.notFound().build();
        }
        }

    @Transactional
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(UploadDto dto, UriComponentsBuilder builder){
        try {
            service.salvar(dto);
            var uri = builder.path("/api/download/{name}").buildAndExpand(dto.name_file()).toUri();
            return ResponseEntity.ok("arquivo salvo \n" + uri);
        } catch (Exception ex){
            System.out.println(ex.fillInStackTrace());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    @Transactional
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<FileDto> deleteFile(@PathVariable String name){
        var file = serviceDelete.delete(name);
        if(file != null) {
            return ResponseEntity.status(HttpStatus.OK).body(file);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @PutMapping(value = "/update/{name}", consumes = "multipart/form-data")
    public ResponseEntity<FileDto> updateFile(UploadDto dto, @PathVariable @NotNull String name) {
        try {
        var file = serviceUpdate.update(dto,name);
        if(file != null) {
            return ResponseEntity.status(HttpStatus.OK).body(file);
        }
    } catch (Exception ex){
            System.out.println(ex.fillInStackTrace());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}

