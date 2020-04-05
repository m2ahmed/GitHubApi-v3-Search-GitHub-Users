package com.exergysystems.githubapiv3searchusersusage.api.interfaces;

import com.exergysystems.githubapiv3searchusersusage.model.implementations.UsersSearchedListResponseModelImpl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubSearchUsersApi {
    //Search username as a query like /search/users?q=thomas
    //@Query annotation completes the endpoints like @Query("q) means ?q={username}
    @GET("/search/users")
    Call<UsersSearchedListResponseModelImpl> getSearchedUsers(@Query("q") String user);
}
