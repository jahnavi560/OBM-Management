package com.example.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
		// fileRepository.findbyFileName(name);
		return null;
	}
	@Override
	public List<FileModel> getFilesByOwner(String email) {
		System.out.println(email + "---------");
		List<FileModel> lst = new ArrayList<>();
		lst = fileRepository.findByOwner(email);
		System.out.println(lst);
		return fileRepository.findByOwner(email);
	}
	@Override
	public List<FileModel> getFilebyOwnerName( String owner,String name){
		return fileRepository.getFilebyOwnerName(owner, name);
	}
}
