package com.gigmatch.demo.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String img;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @DateTimeFormat
    private Date dateTime;

    @OneToOne
    private User owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<PostComment> comments;

    public Post() {
    }

    public Post(String img, String body, Date dateTime, User owner, List<PostComment> comments) {
        this.img = img;
        this.body = body;
        this.dateTime = dateTime;
        this.owner = owner;
        this.comments = comments;
    }

    public Post(long id, String img, String body, Date dateTime, User owner, List<PostComment> comments) {
        this.id = id;
        this.img = img;
        this.body = body;
        this.dateTime = dateTime;
        this.owner = owner;
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }
}
