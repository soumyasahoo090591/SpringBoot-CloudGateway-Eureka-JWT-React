package com.tweetapp.fse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.fse.beans.Login;
import com.tweetapp.fse.repository.LoginRepository;

@Service
public class LoginServiceImpl implements ILoginService {
	@Autowired
	LoginRepository loginRepository;

	/*@Override
	public Login findById(long id) {
		return loginRepository.findById(id).get();
	}*/

	@Override
	public Login findByLoginId(String loginId) {
		return loginRepository.findByLoginId(loginId);
	}
	
	@Override
	public List<Login> getAllUser() {
		return (List<Login>) loginRepository.findAll();
	}

	@Override
	public void addNewUser(Login login) {
		loginRepository.save(login);
	}

	@Override
	public void resetPassword(String loginId, String password) {
		loginRepository.resetPassword(loginId, password);
	}

	@Override
	public Login getUserByFirstName(String firstName) {
		return loginRepository.findByFirstName(firstName);
	}
	
	
	
}
