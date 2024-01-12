package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Song;
import com.example.demo.entity.Users;
import com.example.demo.services.SongService;
//import com.example.demo.services.UserServices;
import com.example.demo.services.UsersServices;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;


@Controller
public class UserController {
	/*
	 * @PostMapping("/register") public String addUsers(@RequestParam("username")
	 * String username, @RequestParam("email") String email,
	 * 
	 * @RequestParam("password") String password, @RequestParam("gender") String
	 * gender,
	 * 
	 * @RequestParam("role") String role, @RequestParam("address") String address )
	 * { System.out.println(username+" "+email+" "+password+" "+gender+" "+role+" "
	 * +address); return "home";
	 * 
	 * 
	 * }
	 */	
	
	
	@Autowired
	UsersServices service;
	
	@Autowired
	SongService songservice;
	
	@PostMapping("/register")
	public String addUsers(@ModelAttribute Users user) {
		boolean userStatus = service.emailExists(user.getEmail());
		if(userStatus ==false) {
		service.addUser(user);
		System.out.println("user added");
		
	}
		else {
			System.out.println("user alrade exit");
		}
		return "home";
	}
	
	@PostMapping("/validate")
	public String validate(@RequestParam("email")String email,
			@RequestParam("password") String password,HttpSession session,Model model) {
		
		if(service.validateUser(email,password)==true) {
			String role=service.getRole(email);
			
			session.setAttribute("email",email);
			if(role.equals("admin")) {
				return "adminHome";
		}
	
		else {
			Users user=service.getUser(email);
			boolean userStatus = user.isPremium();
			List<Song> songList= songservice.fetchAllSongs();
			model.addAttribute("songs", songList );

			model.addAttribute("isPremium", userStatus);
			return "customerHome";
		}
	}
		else {
			return "login";
		}
	}
	
	/*@GetMapping("/pay")
	public String pay(@RequestParam String email) {
		boolean paymentStatus=false;//payment api
		
		if(paymentStatus==true) {
			Users user= service.getUser(email);
			user.setPremium(true);
			service.updateUser(user);
		}
			return "login";
			
			
		}*/
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
		
	}
		
		
	}


