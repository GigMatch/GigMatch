package com.gigmatch.demo.daos;

import com.gigmatch.demo.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Long> {

    //@Query("from Ad as a where a.title like %:term% or a.description like %:term%")

    @Query("from Post a where a.body like %:term%")
    List<Post> searchByTitle(@Param("term") String term);

}
