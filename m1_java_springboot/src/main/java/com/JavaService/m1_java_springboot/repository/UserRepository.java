package com.JavaService.m1_java_springboot.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import com.JavaService.m1_java_springboot.model.*;
import com.JavaService.m1_java_springboot.entity.*;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	 @Query(value = "INSERT INTO tb_user (data) VALUES (?1)",nativeQuery = true)
	 public int Insert(String data);
}