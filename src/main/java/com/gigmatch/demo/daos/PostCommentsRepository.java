package com.gigmatch.demo.daos;

import com.gigmatch.demo.models.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentsRepository extends JpaRepository<PostComment, Long> {
}
