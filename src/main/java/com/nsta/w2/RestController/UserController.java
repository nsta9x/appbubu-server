package com.nsta.w2.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nsta.w2.Models.Credential;
import com.nsta.w2.Models.User;
import com.nsta.w2.Repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> getUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	@PostMapping("/users")
	void addUser(@RequestBody User user) {
	      userRepository.save(user);
	}
	
	@PostMapping("/login")
	private User logIn(@RequestBody Credential credential) {
	      return (User) checkLogin(credential);
	}
	
	private User checkLogin(Credential credential) {
		String e_pass = encode(credential.getUserPassword());
		return userRepository.findUser(credential.getUserLogin(), e_pass);
	}
	
	private String encode(String pw) {
		//TO DO
		return pw;
	}
	
}
