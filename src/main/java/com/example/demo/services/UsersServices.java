package com.example.demo.services;

//import com.example.demo.controller.User;
import com.example.demo.entity.Users;

public interface UsersServices {
	public String addUser(Users user);
	public boolean emailExists(String email);
	public boolean validateUser(String email, String password);
	public String getRole(String email);
	public Users getUser(String email);

	public void updateUser(Users user);
	
	

}
