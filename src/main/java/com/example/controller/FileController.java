package com.example.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.FileModel;
import com.example.service.FileService;

@Controller
public class FileController {

	@Autowired
	private FileService fileService;

	private static  String fpath = "E://jj-Workspace//SpringSecurityLoginTutorial-master//uploaded-files//Mysql.docx";

	@Value("${spring.upload.filepath}")
	private String UPLOADED_FOLDER;

	@PostMapping("/uploadfile") // //new annotation since 4.3
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadstatus";
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			FileModel fileModel = new FileModel();
			fileModel.setName(file.getOriginalFilename());
			fileModel.setPath(path.toString());
			fileService.saveFile(fileModel);

			redirectAttributes.addFlashAttribute("fileName", path.toString());
			redirectAttributes.addFlashAttribute("message","You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/upload-status";
	}

	@GetMapping("/upload-status")
	public String uploadStatus() {
		return "upload-status";
	}

	@RequestMapping("/download/{filename}")
	public void download(@PathVariable("filename") String filename,HttpServletRequest request, HttpServletResponse response) throws IOException {

		FileModel fileModel = fileService.getFile(filename);
		System.out.println(filename+"------------------");
		fpath =  fileModel.getPath();
		File file = new File(fpath);
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());

		}
	}

	@GetMapping("/file-list")
	public ModelAndView fileList() {
		List<FileModel> files = fileService.getAllFile();

		ModelAndView mv = new ModelAndView();
		mv.setViewName("file-list");
		mv.addObject("files", files);
		return mv;
	}

}