package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.FileModel;

@Repository("fileReposiory")
public interface FileRepository extends JpaRepository<FileModel, Integer>{
//	 @Query("SELECT t FROM FileModel t WHERE t.owner = ?1")
	List<FileModel> findByOwner(String owner);
	


}
