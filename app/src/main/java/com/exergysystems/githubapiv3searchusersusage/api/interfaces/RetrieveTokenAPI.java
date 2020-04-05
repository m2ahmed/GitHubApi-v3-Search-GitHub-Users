package com.exergysystems.githubapiv3searchusersusage.api.interfaces;

import com.exergysystems.githubapiv3searchusersusage.model.bean.ApiDetails;
import com.exergysystems.githubapiv3searchusersusage.model.bean.UserDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrieveTokenAPI {
    @GET("/apiaccess.json")
    Call<ApiDetails> listApiDetails();
}
