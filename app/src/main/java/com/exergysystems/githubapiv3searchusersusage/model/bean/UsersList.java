package com.exergysystems.githubapiv3searchusersusage.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersList {

    //Declare user attributes

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("avatar_url")
    @Expose
    private String profileImgURL;

    @SerializedName("html_url")
    @Expose
    private String gitHubProfileURL;

    //Set and Get user attributes

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getProfileImgURL() {
        return profileImgURL;
    }

    public void setProfileImgURL(String profileImgURL) {
        this.profileImgURL = profileImgURL;
    }

    public String getGitHubProfileURL() {
        return gitHubProfileURL;
    }

    public void setGitHubProfileURL(String gitHubProfileURL) {
        this.gitHubProfileURL = gitHubProfileURL;
    }

    //Initialize through Constructor

    public UsersList(String login, String profileImgURL, String gitHubProfileURL) {
        this.login = login;
        this.profileImgURL = profileImgURL;
        this.gitHubProfileURL = gitHubProfileURL;
    }

}
