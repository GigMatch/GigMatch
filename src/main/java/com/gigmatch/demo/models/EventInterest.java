package com.gigmatch.demo.models;

import javax.persistence.*;


// TODO: Verify structure of this table and do we need third column?

@EntityListeners({Event.class})
@Table(name = "event_interests")
public class EventInterest {

    @ManyToOne
    private Event event;

    @OneToOne
    private User user;

    public EventInterest() {
    }

}
