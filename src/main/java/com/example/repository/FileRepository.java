package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.FileModel;

@Repository("fileReposiory")
public interface FileRepository extends JpaRepository<FileModel, Integer>{
//	FileModel findbyFileName(String name);
}
