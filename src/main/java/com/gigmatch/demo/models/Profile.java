package com.gigmatch.demo.models;


import javax.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String profilePhoto;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false, length = 25)
    private String profileType;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(columnDefinition = "TEXT")
    private String interests;

    @Column(columnDefinition = "TEXT")
    private String genres;

    @OneToOne
    private User owner;

    public Profile() { }

    public Profile(String profilePhoto, String city, String state, String profileType, String bio, String interests, String genres, User owner) {
        this.profilePhoto = profilePhoto;
        this.city = city;
        this.state = state;
        this.profileType = profileType;
        this.bio = bio;
        this.interests = interests;
        this.genres = genres;
        this.owner = owner;
    }

    public Profile(long id, String profilePhoto, String city, String state, String profileType, String bio, String interests, String genres, User owner) {
        this.id = id;
        this.profilePhoto = profilePhoto;
        this.city = city;
        this.state = state;
        this.profileType = profileType;
        this.bio = bio;
        this.interests = interests;
        this.genres = genres;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
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

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
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
