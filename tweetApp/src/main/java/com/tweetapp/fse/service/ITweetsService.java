package com.tweetapp.fse.service;

import java.util.List;

import com.tweetapp.fse.beans.Tweets;

public interface ITweetsService {
	
	public List<Tweets> getAllTweets();
	
	public List<Tweets> getTweetsByUser(String loginId);
	
	public void addNewTweet(Tweets tweet);
	
	public void updateUserTweet(String loginId, String threadId, String tweet);
	
	public void removeUserTweets(String loginId, int id);
	
	public void updateLikeCount(String loginId, String threadId, int likeCount);
	
	public Tweets getTweetsForUserIdandThread(String loginId, String tweet_thread_id);
}
