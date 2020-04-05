package com.exergysystems.githubapiv3searchusersusage.api.interfaces;

import com.exergysystems.githubapiv3searchusersusage.model.bean.UsersList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubUserFollowersApi {
    //List user followers details from this API like /users/thomas/followers
    //@Path annotation completes the endpoints like @Path("user) means /username
    //Getting details in json format specifically using these endpoints need authentication
    //You need to generate api access token from your githubProfile by going through settings/developer account settings.
    //More over, you can have a look in module on GitHubAPIClient to see how api access token and username is configured.
    @GET("/users/{user}/followers")
    Call<List<UsersList>> listUserFollowerDetails(@Path("user") String user);

}
