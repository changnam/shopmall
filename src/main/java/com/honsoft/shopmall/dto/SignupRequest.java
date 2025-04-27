package com.honsoft.shopmall.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SignupRequest {
	@NotNull(message = "Name cannot be null")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String userId;
	private String password;
	private String address;
	private String gender;
	private String hobbies;
	private String welcomeWords;
	
	public SignupRequest() {}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	public String getWelcomeWords() {
		return welcomeWords;
	}
	public void setWelcomeWords(String welcomeWords) {
		this.welcomeWords = welcomeWords;
	}
	
	
}
