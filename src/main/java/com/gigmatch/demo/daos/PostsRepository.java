package com.gigmatch.demo.daos;

import com.gigmatch.demo.models.Post;
import com.gigmatch.demo.models.Profile;
import com.gigmatch.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Post, Long> {

    Post findByOwner(User owner);

}
