package com.gigmatch.demo.models;


import javax.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String profile_photo;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false, length = 25)
    private String profile_type;

    @Column
    private String bio;

    @Column
    private String interests;

    @Column
    private String skills;

    @Column
    private String genres;

    @OneToOne
    private User owner;

    public Profile() { }

    public Profile(String profile_photo, String city, String state, String profile_type, String bio, String interests, String skills, String genres, User owner) {
        this.profile_photo = profile_photo;
        this.city = city;
        this.state = state;
        this.profile_type = profile_type;
        this.bio = bio;
        this.interests = interests;
        this.skills = skills;
        this.genres = genres;
        this.owner = owner;
    }

    public Profile(long id, String profile_photo, String city, String state, String profile_type, String bio, String interests, String skills, String genres, User owner) {
        this.id = id;
        this.profile_photo = profile_photo;
        this.city = city;
        this.state = state;
        this.profile_type = profile_type;
        this.bio = bio;
        this.interests = interests;
        this.skills = skills;
        this.genres = genres;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProfile_type() {
        return profile_type;
    }

    public void setProfile_type(String profile_type) {
        this.profile_type = profile_type;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
