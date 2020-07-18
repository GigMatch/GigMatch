package com.gigmatch.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.xml.bind.v2.TODO;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post_comments")

public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    private Date createDate;


    @ManyToOne
    @JsonBackReference
    private Post post;

    @ManyToOne
    private PostComment postComment;

    @OneToOne
    private User owner;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createdAt;



    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public PostComment() {
    }

    public PostComment(Date createdAt, Post post, User owner, String content) {
        this.createDate = createdAt;
        this.post = post;
        this.owner = owner;
        this.content = content;
    }

    public PostComment(long id, Date createdAt, Post post, User owner, String content) {
        this.id = id;
        this.createDate = createdAt;
        this.post = post;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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