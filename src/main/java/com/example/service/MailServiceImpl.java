package com.example.service;

import java.io.File;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service("mailService")
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender sender;
	
	@Value("${spring.upload.filepath}")
	private String UPLOADED_FOLDER;
	
	private String fileName = "Mysql.docx";
	
	@Override
	public String sendMail(String email) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
 			helper.setTo(email);
			helper.setText("Kindly upload our timesheet");
			helper.setSubject("Weekly Timesheet Reminder");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}
	@Override
	public String sendMailtoAllPendingUsers(List<String> eParams) {
		
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			String[] to = eParams.toArray(new String[eParams.size()]);
 			helper.setTo(to);
 			helper.setText("Kindly upload our timesheet)");
			helper.setSubject("Weekly Timesheet Reminder");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		//sender.send(message);
		return "Mail Sent Success!";
	}
	
	@Override
	public String sendMailtoAdmin(String mail) {
		
		MimeMessage message = sender.createMimeMessage();
		
		FileSystemResource file = new FileSystemResource(UPLOADED_FOLDER + "" + fileName);
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
 			helper.setTo(mail);
 			helper.setText("Kindly Download All timesheets)");
			helper.setSubject("Weekly Timesheet Reminder");
			System.out.println("Service---------");
			helper.addAttachment(file.getFilename(), file);
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
	sender.send(message);
		return "Mail Sent Success!";
	}

}
