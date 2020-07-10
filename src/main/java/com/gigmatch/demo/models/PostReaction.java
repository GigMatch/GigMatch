package com.gigmatch.demo.models;

import javax.persistence.*;

// TODO: Verify structure and third column content?

@Entity
@Table(name = "post_reactions")
public class PostReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Post post;

    @OneToOne
    private User user;

    @Column
    private String reactionType;


    public PostReaction() {
    }
}
