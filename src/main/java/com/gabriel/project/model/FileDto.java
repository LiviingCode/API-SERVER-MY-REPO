package com.gabriel.project.model;

public record FileDto(String name, String type, byte[] file) {
    public FileDto(String nameFile, String typeFile){
        this(nameFile, typeFile, null);
    }
}
