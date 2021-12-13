package com.tweetapp.fse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.fse.beans.Login;
import com.tweetapp.fse.beans.Tweets;
import com.tweetapp.fse.repository.LoginRepository;
import com.tweetapp.fse.repository.TweetsRepository;

@Service
public class TweetsServiceImpl implements ITweetsService {
	
	@Autowired
	TweetsRepository tweetsRepository;

	@Override
	public List<Tweets> getAllTweets() {
		return (List<Tweets>) tweetsRepository.findAll();
	}

	@Override
	public List<Tweets> getTweetsByUser(String loginId) {
		return tweetsRepository.findByLoginId(loginId);
	}

	@Override
	public void addNewTweet(Tweets tweet) {
		tweetsRepository.save(tweet);
	}

	@Override
	public void updateUserTweet(String loginId, String threadId, String tweet) {
		 tweetsRepository.updateUserTweet(loginId, threadId, tweet);
	}

	
	@Override
	public void removeUserTweets(String loginId, int id) {
		tweetsRepository.removeUserTweets(loginId, id);
	}
	
	
	@Override
	public void updateLikeCount(String loginId, String threadId, int likeCount) {
		tweetsRepository.updateLikCount(loginId, threadId, likeCount);
	}

	@Override
	public Tweets getTweetsForUserIdandThread(String loginId, String tweet_thread_id) {
		return tweetsRepository.getTweetsForUserIdandThread(loginId, tweet_thread_id);
	}

	
	
}
