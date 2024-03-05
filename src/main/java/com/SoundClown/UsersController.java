package com.SoundClown;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
*/

@Controller
public class UsersController {

	/*
	@Autowired
	private UsersService users_service;
	*/

	@GetMapping("/")
	public String user_home() {
		System.out.println("Hello");
		return "index";
	}

	/*
	@PostMapping(value = "/add_user")
	public String add_user(@RequestParam("username") String username,
						   @RequestParam("password") String password,
						   Model model) {
		users_service.add_user(username, password);
		System.out.println("username = " + username + ",password= " + password);
		return "redirect:/";
	}

	@GetMapping(value = "/delete")
	public String delete_user(@RequestParam("username") String username,
							  @RequestParam("id") int id) {
		users_service.delete_user(username, id);
		System.out.println("User = " + username + "was removed from the database."); 
		return "Whoops";
	}
	*/
}
