package com.exergysystems.githubapiv3searchusersusage.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetails {

    //Declare user attributes

    @SerializedName("avatar_url")
    @Expose
    private String avatar_url;

    @SerializedName("html_url")
    @Expose
    private String html_url;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("public_repos")
    @Expose
    private String repos;

    @SerializedName("followers")
    @Expose
    private String followers;

    @SerializedName("following")
    @Expose
    private String following;

    @SerializedName("location")
    @Expose
    private String location;

    //Set and Get user attributes

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return company;
    }

    public void setCompanyName(String company) {
        this.company = company;
    }

    public String getFollowersNumber() {
        return followers;
    }

    public void setFollowersNumber(String followers) {
        this.followers = followers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublicRepositories() {
        return repos;
    }

    public void setRepos(String repos) {
        this.repos = repos;
    }

    //Initialize Through Constructor

    public UserDetails(String name, String email ,String company, String repos, String followers, String following, String location) {
        this.name = name;
        this.company = company;
        this.email = email;
        this.repos = repos;
        this.followers = followers;
        this.following = following;
        this.location = location;
    }
}
