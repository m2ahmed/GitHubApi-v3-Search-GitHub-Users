package com.exergysystems.githubapiv3searchusersusage.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiDetails {

    //Declare user attributes

    public String getApiAccessToken() {
        return apiAccessToken;
    }

    public void setApiAccessToken(String apiAccessToken) {
        this.apiAccessToken = apiAccessToken;
    }

    @SerializedName("apiaccesstoken")
    @Expose
    private String apiAccessToken;

    //Initialize Through Constructor

    public ApiDetails(String apiAccessToken) {
        this.apiAccessToken = apiAccessToken;
    }
}
