package com.nsta.w2.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nsta.w2.Models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	List<User> findAll();
	
	@Query(
			value = "SELECT * FROM user u WHERE u.password = ?1 AND (u.account = ?2 OR u.email = ?2)",
			nativeQuery = true)
	User findUser(String e_pass, String userLogin);
}