package com.tweetapp.fse.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.tweetapp.fse.beans.Login;
import com.tweetapp.fse.service.ILoginService;

@CrossOrigin
@RestController
@RequestMapping(value={"/api/v1.0/tweets"})
public class LoginController {
	
	@Autowired
	ILoginService loginService;
	
	// Search  by id 
 /*   @GetMapping(value = "/tweets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Login> getUserById(@PathVariable("id") long id) {
        System.out.println("Fetching product with id " + id);
        Login login = loginService.findById(id);
        if (login == null) {
            return new ResponseEntity<Login>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Login>(login, HttpStatus.OK);
    }
   */ 
	
    // Search  by Login Id 
/*    @GetMapping(value = "/{loginId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Login> getUserByLoginId(@PathVariable("loginId") String loginId) {
        System.out.println("Fetching  with id " + loginId);
        Login login = loginService.findByLoginId(loginId);
        if (login == null) {
            return new ResponseEntity<Login>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Login>(login, HttpStatus.OK);
    }
  */
	
	// 1. Register a User: custom query 
    @PostMapping(value="/register",headers="Accept=application/json")
	 public ResponseEntity<Void> addNewUser(@RequestBody Login login, UriComponentsBuilder ucBuilder){
	     System.out.println("Adding New User :: "+login.getFirstName());
	     loginService.addNewUser(login);
	     HttpHeaders headers = new HttpHeaders();
	     headers.setLocation(ucBuilder.path("/tweets/{id}").buildAndExpand(login.getId()).toUri());
	     return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	 }
	
 // 2. Login a User
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserLogin( @RequestBody Login login) {
        Login user = loginService.findByLoginId(login.getLoginId());
        String message = "";
        if (user == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }else {
        	if(login.getLoginId().equals(user.getLoginId()) && login.getPassword().equals(user.getPassword())) {
        		message = "Login Successful";
        	}else {
        		message = "Invalid User or Password";
        	}
        }
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }
    
    // 3. Forgot password.. reset password
    @PostMapping(value = "/forgot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resetPassword(@RequestBody Login login) {
        Login user = loginService.findByLoginId(login.getLoginId());
        String message = "";
        if (user == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }else {
        	if(user.getLoginId().equalsIgnoreCase(login.getLoginId())) {
        		loginService.resetPassword(login.getLoginId(), login.getPassword());
            	message = "Password reset successfully !!";
        	}else {
        		message = "User not found !!";
        	}
        	
        }
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }
    
    // 5. Search all Users
    @GetMapping(value="/users/all", headers="Accept=application/json")
	 public ResponseEntity<List<Login>> getAllUsers() {	 
	  List<Login> userList=loginService.getAllUser();
	  if (userList.size()==0) {
          return new ResponseEntity<List<Login>>(HttpStatus.NOT_FOUND);
      }
	  return new ResponseEntity<List<Login>>(userList,HttpStatus.OK);
	 }
    
    // 6. Get User by Name
    @GetMapping(value = "/user/search/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserByName(@PathVariable("userName") String userName) {
        System.out.println("Fetching  with user name " + userName);
        Login login = loginService.getUserByFirstName(userName);
        String message = "";
        if (login == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }else {
        	message = "User " + login.getFirstName().toUpperCase() + " " + login.getLastName().toUpperCase() + " searches Successfully" + ".";
        }
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }
}
