package com.example.service;

import java.util.List;

import com.example.model.FileModel;

public interface FileService {
	public void saveFile(FileModel file);
	public List<FileModel> getAllFile() ;
	public FileModel getFile(String name);
	public List<FileModel> getFilesByOwner(String email);
	List<FileModel> getFilebyOwnerName( String owner,String name);
	public void deleteFile(Integer id);
}
