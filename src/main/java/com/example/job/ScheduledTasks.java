package com.example.job;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.model.User;
import com.example.service.MailService;
import com.example.service.UserService;

@Component
public class ScheduledTasks {

	@Autowired
	private MailService mailService;
	@Autowired
	private UserService userService;
	
	@Value("${quartz.enabled}")
	private boolean isEnable;
	
	@Value("${quartz.enabled.sendmail.allpendingusers}")
	private boolean isEnabletoAll;
	
	@Value("${quartz.enabled.sendmail.admin}")
	private boolean isEnableAdmin;
	
	@Value("${spring.admin.mail}")
	private String adminEmail;

	@Scheduled(cron = "${job.expression}")
	public void reportCurrentTime() {

		if (!isEnable) {
			return;
		} else {
			System.out.println("jj job is running");
		}
	}
	
	
	@Scheduled(cron = "${job.expression.sendmail.allpendingusers}")
	public void sendMailtoAllPendingUsers() {
		System.out.println("sendMailtoAllPendingUsers --------jj job is running");
		if (!isEnabletoAll) {
			System.out.println("Mail service disabled");
			return;
		} else {
			List<User> lstuser = userService.getFilePendingUser();
			List<String> list = new ArrayList<String>();
			for(User user : lstuser) {
				list.add(user.getEmail());
			}
			mailService.sendMailtoAllPendingUsers(list);
			System.out.println("jj** job is running");
		}
	}
	
	@Scheduled(cron = "${job.expression.sendmail.admin}")
	public void sendMailtoAdmin() {
		System.out.println("sendMailtoAdmin --------jj job is running");
		if (!isEnableAdmin) {
			System.out.println("Mail service disabled");
			return;
		} else {
			System.out.println("admin mail--"+adminEmail);
			mailService.sendMailtoAdmin(adminEmail);
			System.out.println("jj** job is running");
		}
	}
}