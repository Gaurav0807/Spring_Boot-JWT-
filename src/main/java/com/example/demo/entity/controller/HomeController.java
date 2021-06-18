package com.example.demo.entity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.AuthRequest;
import com.example.demo.util.JwtUtil;

@RestController
public class HomeController {
	
	
	@Autowired
	private JwtUtil j;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/hyy")
	public String home()
	{
		return "welcome Gaurav Rawat";
	}
	
	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest a) throws Exception
	{
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(a.getUserName(), a.getPassword()));
		}
		catch(Exception e)
		{
			throw new Exception("invalid username/password");
		}
		return j.generateToken(a.getUserName());
	}
}
