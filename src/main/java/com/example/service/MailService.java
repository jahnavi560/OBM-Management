package com.example.service;

import java.util.List;

public interface MailService {
	public String sendMail(String email);
	public String sendMailtoAllPendingUsers(List<String> eParams) ;
	public String sendMailtoAdmin(String mail);
}
