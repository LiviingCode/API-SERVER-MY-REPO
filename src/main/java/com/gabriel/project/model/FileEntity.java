package com.gabriel.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "file_entity", uniqueConstraints = {@UniqueConstraint(name = "unique_name", columnNames = "name")})
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String  name;

    private String type;
    @Lob
    private byte[] file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public FileEntity(){
    }

    public FileEntity(String nome, String type, byte[] file) {
    this.name = nome;
    this.type = type;
    this.file = file;
    }
}
