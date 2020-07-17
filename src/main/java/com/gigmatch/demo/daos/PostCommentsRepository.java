package com.gigmatch.demo.daos;

import com.gigmatch.demo.models.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentsRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findCommentsByPostId(long id);
}
