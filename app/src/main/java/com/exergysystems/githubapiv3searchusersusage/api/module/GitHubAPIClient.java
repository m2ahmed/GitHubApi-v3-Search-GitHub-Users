package com.exergysystems.githubapiv3searchusersusage.api.module;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubAPIClient {
    private static long TIME_OUT = 30;
    private static final String BASE_URL = "https://api.github.com";
    private static Retrofit retrofit = null;


    private static OkHttpClient getOkHttpClient(final String apiaccesstoken)
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder requestBuilder = chain.request().newBuilder();
                        requestBuilder.addHeader("Authorization", Credentials.basic("ItsYourAhmed1", apiaccesstoken));
                        return chain.proceed(requestBuilder.build());
                    }
                });
        return builder.build();
    }

    public static Retrofit getClient(String apiaccesstoken)
    {
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient(apiaccesstoken))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
