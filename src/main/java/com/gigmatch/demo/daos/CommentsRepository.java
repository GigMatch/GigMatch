package com.gigmatch.demo.daos;

import com.gigmatch.demo.models.Post;
import com.gigmatch.demo.models.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository <PostComment, Long>{

}
