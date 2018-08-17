package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.FileModel;
import com.mysql.cj.x.protobuf.MysqlxCrud.FindOrBuilder;

import java.lang.String;

@Repository("fileReposiory")
public interface FileRepository extends JpaRepository<FileModel, Integer>{
//	 @Query("SELECT t FROM FileModel t WHERE t.owner = ?1")
//	List<FileModel> findByOwner(String owner);
	List<FileModel> getFilebyOwnerName(@Param(value="owner") String owner,@Param(value="name") String name);
	List<FileModel> findByOwner(String owner);
}
