package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.FileModel;
import com.example.model.User;
import com.example.service.FileService;
import com.example.service.UserService;

@Controller
public class LoginController {
	private static final Logger logger = LogManager.getLogger(LoginController.class);
	@Autowired
	private UserService userService;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		logger.debug("login log");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		List<User> lstuser = userService.findAll();
		modelAndView.addObject("users",lstuser);
		modelAndView.addObject("welcomeMsg", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("userName", user.getName() + " " + user.getLastName() );
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	@RequestMapping(value="/admin-login")
	public ModelAndView jhome(){
		logger.info("info");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		List<User> lstuser = userService.findAll();
		modelAndView.addObject("users",lstuser);
		modelAndView.addObject("welcomeMsg", "Welcome " + user.getName() + " " + user.getLastName());
		modelAndView.addObject("userName", user.getName() + " " + user.getLastName() );
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin-login");
		return modelAndView;
	}
	@RequestMapping(value="/employee-login")
	public ModelAndView employeeLogin(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee-login");
		return modelAndView;
	}
	@RequestMapping(value="/delete-user")
	public String deleteUser(@Valid User user, BindingResult bindingResult) {
		userService.deleteUser(user);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/home");
		return "redirect:/admin/home";
	}

}
