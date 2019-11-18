package com.JavaService.m1_java_springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.JavaService.m1_java_springboot.entity.*;
import com.JavaService.m1_java_springboot.model.PostModel;

@Repository("PostRepository")
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
	 @Query(value = "SELECT * FROM public.tb_post p WHERE p.user_id = :user_id ",nativeQuery = true)
	 public List<PostEntity> GetPostByUserId(@Param("user_id") String user_id);
	 
	 @Query(value = "SELECT * FROM public.tb_post p WHERE p.post_title LIKE CONCAT('%',:post_title,'%')",nativeQuery = true)
	 public List<PostEntity> GetPostByTitle(@Param("post_title") String post_title);
	 
//	 @Query(value = "SELECT filterPost(:postid,:userid,:posttitle,:postcreatedat)",nativeQuery = false)
	 @Procedure(value = "filterPost")
	 public List<Object> filterPost(
			 @Param("postid") String postid,
			 @Param("userid") String userid,
			 @Param("posttitle") String posttitle,
			 @Param("postcreatedat") Long postcreatedat
	);
	 
}
