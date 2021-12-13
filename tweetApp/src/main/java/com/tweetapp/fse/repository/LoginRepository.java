package com.tweetapp.fse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tweetapp.fse.beans.Login;


@Repository
public interface LoginRepository extends CrudRepository<Login, Long>
{

//	Optional<Login> findById(Long id);
	
	Login findByLoginId(String loginId);
	
	Login findByFirstName(String firstName);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE Login l SET l.password = ?2 WHERE l.login_id = ?1", nativeQuery = true)
	public void resetPassword(String loginId, String password);
	
	
	
	
}
