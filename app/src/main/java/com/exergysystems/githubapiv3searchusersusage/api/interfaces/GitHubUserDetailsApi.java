package com.exergysystems.githubapiv3searchusersusage.api.interfaces;

import com.exergysystems.githubapiv3searchusersusage.model.bean.UserDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubUserDetailsApi {
    //List all user public details from this API like /users/thomas
    //@Path annotation completes the endpoints like @Path("user) means /user
    @GET("/users/{user}")
    Call<UserDetails> listUserDetails(@Path("user") String user);

}
