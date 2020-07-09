package com.gigmatch.demo.models;

import javax.persistence.*;

// TODO: Verify structure and third column content?

@EntityListeners(Post.class)
@Table(name = "post_reactions")
public class PostReaction {

    @ManyToOne
    private Post post;

    @OneToOne
    private User user;

    @Column
    private String reactionType;


    public PostReaction() {
    }
}
