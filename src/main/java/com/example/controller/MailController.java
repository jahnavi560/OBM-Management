package com.example.controller;

import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
	@Autowired
	private JavaMailSender sender;

	@RequestMapping("/sendto-all/[{eParams}]")
	public String sendMailtoAll(@PathVariable("eParams") List<String> eParams) {
		
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
		sender.send(message);
		return "Mail Sent Success!";
	}
	
	@RequestMapping("/send-mail/{email}")
	public String sendMail(@PathVariable("email") String email) {
		
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
 			helper.setTo(email);
			helper.setText("Kindly upload our timesheet)");
			helper.setSubject("Weekly Timesheet Reminder");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}
}