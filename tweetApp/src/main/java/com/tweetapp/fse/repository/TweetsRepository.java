package com.tweetapp.fse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tweetapp.fse.beans.Tweets;


@Repository
public interface TweetsRepository extends CrudRepository<Tweets, Long>
{

//	Optional<Login> findById(Long id);

	List<Tweets> findByLoginId(String loginId);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE Tweets t SET t.tweets_contents = ?3 WHERE t.login_id = ?1 and t.tweet_thread_id = ?2", nativeQuery = true)
	public void updateUserTweet(String loginId, String threadId, String tweet);
	
	
	@Modifying
	@Transactional
//	@Query("DELETE FROM Tweets t WHERE t.login_id = ?1 and t.id = ?2")
	@Query("DELETE FROM Tweets t WHERE t.id = ?2")
	void removeUserTweets(String loginId, int id);
	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE Tweets t SET t.like = ?3 WHERE t.login_id = ?1 and t.tweet_thread_id = ?2", nativeQuery = true)
	public void updateLikCount(String loginId, String threadId, int likeCount);
	
	@Query(value ="Select * FROM Tweets t WHERE t.login_id = ?1 and t.tweet_thread_id = ?2", nativeQuery = true)
	@Transactional
	Tweets getTweetsForUserIdandThread(String loginId, String tweet_thread_id);
	
	
}
