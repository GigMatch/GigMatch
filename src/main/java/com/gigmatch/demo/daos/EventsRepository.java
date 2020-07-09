package com.gigmatch.demo.daos;

import com.gigmatch.demo.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Event, Long> {


}
