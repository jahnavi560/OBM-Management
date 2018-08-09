package org.jj.controller;

import java.util.List;

import org.jj.model.User;
import org.jj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage() {
		return "home";
	}

	@RequestMapping(value = "/loginUser", method = RequestMethod.GET)
	public String login(@ModelAttribute("user") User user) {
		return "loginUser";

	}
	@RequestMapping(value = "/validate-user", method = RequestMethod.POST)
	public String validate(@ModelAttribute("user") User user) {
		boolean isValidate = userService.validateUser(user);
		if(isValidate) {
			return "redirect:/userlist";
		}else {
			return "home";
		}

	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.GET)
	public String register(@ModelAttribute("user") User user) {
		return "registerUser";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User user,Model model) {
		if (user.getId() == 0) {
			userService.addUser(user);
		} else {
			userService.updateUser(user);
		}
		model.addAttribute("user", user);
		return "registration-success";
	}
	@RequestMapping(value = "/registration-success", method = RequestMethod.GET)
	public String registrationSuccess() {
		return "registration-success";
	}
	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	public String userList(Model model) {

		List<User> listOfUsers = userService.getAllUsers();
		model.addAttribute("user", new User());
		model.addAttribute("listOfUsers", listOfUsers);
		return "userlist";
	}

	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") int id) {
		userService.deleteUser(id);
		return "redirect:/userlist";

	}

	@RequestMapping(value = "/updateUser/{id}", method = RequestMethod.GET)
	public String updateUser(@PathVariable("id") int id, Model model) {
		model.addAttribute("user", this.userService.getUser(id));
		model.addAttribute("listOfUsers", this.userService.getAllUsers());
		return "registerUser";
	}

	@RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable int id) {
		return userService.getUser(id);
	}

	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public String getUsers(Model model) {

		List<User> listOfUsers = userService.getAllUsers();
		model.addAttribute("user", new User());
		model.addAttribute("listOfUsers", listOfUsers);
		return "userDetails";
	}

/*	@RequestMapping(value = "/addUserold", method = RequestMethod.POST)
	public String addUserold(@ModelAttribute("user") User user) {
		if (user.getId() == 0) {
			userService.addUser(user);
		} else {
			userService.updateUser(user);
		}

		return "redirect:/getAllUsers";
	}

	@RequestMapping(value = "/updateUserold/{id}", method = RequestMethod.GET)
	public String updateUserold(@PathVariable("id") int id, Model model) {
		model.addAttribute("user", this.userService.getUser(id));
		model.addAttribute("listOfUsers", this.userService.getAllUsers());
		return "userDetails";
	}

	@RequestMapping(value = "/deleteUserold/{id}", method = RequestMethod.GET)
	public String deleteUserold(@PathVariable("id") int id) {
		userService.deleteUser(id);
		return "redirect:/getAllUsers";

	}*/
}
