package com.gigmatch.demo.models;

import javax.persistence.*;


// TODO: Verify structure of this table and do we need third column?

@Entity
@Table(name = "event_interests")
public class EventInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Event event;

    @OneToOne
    private User user;

    public EventInterest() {
    }

}
