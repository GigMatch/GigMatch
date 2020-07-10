package com.gigmatch.demo.models;

import com.sun.xml.bind.v2.TODO;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post_comments")
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    private Date createDate;

    //TODO: Verify relationship for the two below
    @ManyToOne
    private Post post;

    @OneToOne
    private User owner;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public PostComment() {
    }

    public PostComment(Date createDate, Post post, User owner, String content) {
        this.createDate = createDate;
        this.post = post;
        this.owner = owner;
        this.content = content;
    }

    public PostComment(long id, Date createDate, Post post, User owner, String content) {
        this.id = id;
        this.createDate = createDate;
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