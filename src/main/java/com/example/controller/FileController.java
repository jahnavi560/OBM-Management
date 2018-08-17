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
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.FileModel;
import com.example.service.FileService;

@Controller
public class FileController {
	private static final Logger logger = LogManager.getLogger(FileController.class);
	@Autowired
	ServletContext context;
	
	@Autowired
	private FileService fileService;

	@Value("${spring.upload.filepath}")
	private String UPLOADED_FOLDER;

	@PostMapping("/uploadfile")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadstatus";
		}
		try {
			logger.debug("Debug log");
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userName = auth.getName();
			String filename = file.getOriginalFilename();
			List<FileModel> lstfile = fileService.getFilebyOwnerName(userName, filename);
			System.out.println("lst-----------"+lstfile);
			
			if(!lstfile.isEmpty()) {
				
			}else {
				
			}
			
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + "" + file.getOriginalFilename());
			System.out.println(path);
			Files.write(path, bytes);
			FileModel fileModel = new FileModel();
			fileModel.setName(file.getOriginalFilename());
			fileModel.setPath(path.toString());
			fileModel.setOwner(userName);
			java.util.Date d = new Date();
			java.sql.Timestamp sqlDate=new java.sql.Timestamp(d.getTime());
			fileModel.setLastmodified_date(sqlDate);
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

	@RequestMapping(value = "/download/{file:.+}", method = RequestMethod.GET)
	public void download(@PathVariable("file") String filename, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		File file = new File(UPLOADED_FOLDER + "" + filename);
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

	@RequestMapping("/file-list/{email:.+}/{user}")
	public ModelAndView fileList(@PathVariable("email") String owner, @PathVariable("user") String user) {
		List<FileModel> files = fileService.getFilesByOwner(owner);
		System.out.println(files);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("file-list");
		mv.addObject("files", files);
		mv.addObject("filelistmsg", "File list by " +user);
		return mv;
	}
	@RequestMapping("/file-list")
	public ModelAndView getAllfileList() {
		List<FileModel> files = fileService.getAllFile(); 
		System.out.println(files);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("file-list");
		mv.addObject("files", files);
		return mv;
	}
}
