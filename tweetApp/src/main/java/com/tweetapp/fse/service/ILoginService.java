package com.tweetapp.fse.service;

import java.util.List;

import com.tweetapp.fse.beans.Login;

public interface ILoginService {
		
//	public Login findById(long id);
	
	public Login findByLoginId(String loginId);
	
	public List<Login> getAllUser();
	
	public void addNewUser(Login login);
	
	public void resetPassword(String loginId, String password);
	
	public Login getUserByFirstName(String firstName);
}
