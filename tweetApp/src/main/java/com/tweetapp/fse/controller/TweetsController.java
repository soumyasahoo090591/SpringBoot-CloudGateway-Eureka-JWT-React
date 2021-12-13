package com.tweetapp.fse.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.tweetapp.fse.beans.Tweets;
import com.tweetapp.fse.service.ILoginService;
import com.tweetapp.fse.service.ITweetsService;

@CrossOrigin
@RestController
@RequestMapping(value={"/api/v1.0/tweets"})
public class TweetsController {
	
	@Autowired
	ITweetsService tweetsService;
  
	@Autowired
	ILoginService loginService;
	
    // 4. Search all Tweets
    @GetMapping(value="/all", headers="Accept=application/json")
	 public ResponseEntity<List<Tweets>> getAllTweets() {	 
	  List<Tweets> tweetsList=tweetsService.getAllTweets();
	  List<String> tweets = new ArrayList<>();
	  tweets = tweetsList.stream()
	  .filter(twt->twt.getTweetsContents()!=null)
	  .map(twt->twt.getTweetsContents())
	  .collect(Collectors.toList());
	  if (tweets.size()==0) {
          return new ResponseEntity<List<Tweets>>(HttpStatus.NOT_FOUND);
      }
	  return new ResponseEntity<List<Tweets>>(tweetsList,HttpStatus.OK);
	 }

    // 7. Get all tweets of user
    @SuppressWarnings("unused")
	@GetMapping(value = "/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getTweetsByUserName(@PathVariable("userName") String userName) {
        System.out.println("Fetching tweets with userName " + userName);
        Login login = loginService.getUserByFirstName(userName);
        String loginId = login.getLoginId();
        List<String> messages = new ArrayList<>();
        if (login == null) {
        	messages.add("");
            return new ResponseEntity<List<String>>(messages,HttpStatus.NOT_FOUND);
        }
        List<Tweets> tweets = tweetsService.getTweetsByUser(loginId); 
        messages=tweets.stream().filter(twt->twt.getTweetsContents()!=null).map(twt->twt.getTweetsContents()).collect(Collectors.toList());
        return new ResponseEntity<List<String>>(messages, HttpStatus.OK);
    }
    
    // 8. Post new tweet
    @PostMapping(value="/{loginId}/add",headers="Accept=application/json")
	 public ResponseEntity<Void> addNewTweet(@RequestBody Tweets tweet, @PathVariable("loginId") String loginId, UriComponentsBuilder ucBuilder){
    	 System.out.println("Adding tweets for "+loginId);
    	 tweet.setLoginId(loginId);
    	 tweet.setTweetThreadId(loginId+"_"+1);
    	 tweetsService.addNewTweet(tweet);
	     HttpHeaders headers = new HttpHeaders();
	     headers.setLocation(ucBuilder.path("/tweets/{id}").buildAndExpand(tweet.getId()).toUri());
	     return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	 }
    
    // 9. Update tweet
    @PutMapping(value = "/{loginId}/update/{threadId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resetPassword(@PathVariable("loginId") String loginId,
    		@PathVariable("threadId") String threadId,
			@RequestParam(name = "tweet", required = true) String tweet) {
		System.out.println("tweet update for id " + loginId);
		tweetsService.updateUserTweet(loginId, threadId, tweet);
		String message = "tweet has been updated !!";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
    
    
    // 10. Delete by Name : custom query ...in progress
    @DeleteMapping(value="/{loginId}/delete/{id}", headers ="Accept=application/json")
	public ResponseEntity<String> deleteProductByName(@PathVariable String loginId, @PathVariable int id){
    	tweetsService.removeUserTweets(loginId, id);
    	return new ResponseEntity<String>("Tweet has been deleted... " ,HttpStatus.ACCEPTED);
	}
    
    
    // 11. Like tweets
    @PutMapping(value = "/{loginId}/like/{threadId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resetPassword(@PathVariable("loginId") String loginId,
    		@PathVariable("threadId") String tweetThreadId) {
		System.out.println(" update the like count for :: " + loginId);
		
		Tweets tweets = tweetsService.getTweetsForUserIdandThread(loginId, tweetThreadId);
		String count = tweets.getLikeCount();
		int newLikeCount = Integer.parseInt(count) + 1;
		tweetsService.updateLikeCount(loginId, tweetThreadId, newLikeCount);
		String message = "Like has been updated !!";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
}
