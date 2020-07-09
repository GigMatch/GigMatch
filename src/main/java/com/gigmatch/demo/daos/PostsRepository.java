package com.gigmatch.demo.daos;

import com.gigmatch.demo.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Post, Long> {



}
