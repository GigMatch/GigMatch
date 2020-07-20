package com.gigmatch.demo.daos;

import com.gigmatch.demo.models.Event;
import com.gigmatch.demo.models.Post;
import com.gigmatch.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventsRepository extends JpaRepository<Event, Long> {

    @Query("from Event as a where a.description like %:term% or a.city like %:term% or a.zipcode like %:term%")
    List<Event> searchByDescription(@Param("term") String term);
}
