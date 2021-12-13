package com.tweetapp.fse.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tweetstable")
public class Tweets {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="login_id")
	private String loginId;
	
	@Column(name="tweets_contents")
	private String tweetsContents;
	
	@Column(name="like_count")
	private String likeCount;

	@Column(name="tweet_thread_id")
	private String  tweetThreadId;
	
	public String getTweetThreadId() {
		return tweetThreadId;
	}

	public void setTweetThreadId(String tweetThreadId) {
		this.tweetThreadId = tweetThreadId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getTweetsContents() {
		return tweetsContents;
	}

	public void setTweetsContents(String tweetsContents) {
		this.tweetsContents = tweetsContents;
	}

	public String getLikeCount() {
		return likeCount;
	}

	public void setLike(String likeCount) {
		this.likeCount = likeCount;
	}
	
}
