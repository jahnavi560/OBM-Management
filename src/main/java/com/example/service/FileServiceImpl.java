package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.FileModel;
import com.example.repository.FileRepository;

@Service("fileService")
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileRepository fileRepository;

	@Override
	public void saveFile(FileModel file) {
		fileRepository.save(file);

	}
	@Override
	public List<FileModel> getAllFile() {
		return fileRepository.findAll();

	}
	@Override
	public FileModel getFile(String name) {
		return null;
				//fileRepository.findbyFileName(name);
				
				
	}

}
