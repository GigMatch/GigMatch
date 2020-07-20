package com.gigmatch.demo.daos;

import com.gigmatch.demo.models.Post;
import com.gigmatch.demo.models.PostComment;
import com.gigmatch.demo.models.Profile;
import com.gigmatch.demo.models.User;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Long> {

    @Query("from Post as a where a.body like %:term%")
    List<Post> searchByBody(@Param("term") String term);

    List<Post> findAllByOwner(User owner);

}
