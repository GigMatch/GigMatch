package com.gigmatch.demo.models;

import com.sun.xml.bind.v2.TODO;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event_comments")
public class EventComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    private Date createDate;

    //TODO: Verify relationship for the two below
    @ManyToOne
    private Event event;

    @OneToOne
    private User owner;


    @Column(nullable = false, length = 20)
    private String content;


    public EventComment() {
    }

    public EventComment(Date createDate, Event event, User owner, String content) {
        this.createDate = createDate;
        this.event = event;
        this.owner = owner;
        this.content = content;
    }

    public EventComment(long id, Date createDate, Event event, User owner, String content) {
        this.id = id;
        this.createDate = createDate;
        this.event = event;
        this.owner = owner;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
