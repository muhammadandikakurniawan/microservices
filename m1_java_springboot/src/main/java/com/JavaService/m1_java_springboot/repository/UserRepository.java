package com.JavaService.m1_java_springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.JavaService.m1_java_springboot.model.*;
import com.JavaService.m1_java_springboot.entity.*;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	 @Query(value = "INSERT INTO tb_user (data) VALUES (?1)",nativeQuery = true)
	 public Object InsertUser(String data);

	 @Query(value = "SELECT * FROM public.tb_user usr WHERE usr.user_id = concat(',:id,')",nativeQuery = true)
	 public List<UserEntity> getUserById(@Param("id") String id);

}