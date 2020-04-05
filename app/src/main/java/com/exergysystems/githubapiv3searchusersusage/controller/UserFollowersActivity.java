package com.exergysystems.githubapiv3searchusersusage.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.exergysystems.githubapiv3searchusersusage.R;
import com.exergysystems.githubapiv3searchusersusage.api.module.GitHubAPIClient;
import com.exergysystems.githubapiv3searchusersusage.api.interfaces.GitHubUserFollowersApi;
import com.exergysystems.githubapiv3searchusersusage.model.adapters.GitHubSearchedUserFollowersAdapter;
import com.exergysystems.githubapiv3searchusersusage.model.bean.UsersList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFollowersActivity extends AppCompatActivity {

    private static final String TAG = "MyTag";
    private RecyclerView githubUserListRecyclerView;
    private RecyclerView.Adapter githubUserListAdapter;
    private RecyclerView.LayoutManager githubUserListLayoutManager;
    private UsersList item;
    ProgressDialog progressDialog;

    String username = "";
    String profileLink = "";
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_followers);
        initViews();
        loadJSON();
    }

    //Initialize views

    private void initViews()
    {
        if(getIntent().getStringExtra("username")!=null) {
            username = getIntent().getStringExtra("username");
            profileLink = getIntent().getStringExtra("profileLink");
            token = getIntent().getStringExtra("token");
            try {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(username + " Followers List");
            }catch (Exception e){
                Log.e(TAG, "ActionBar: "+e.getMessage() );
            }
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Fetching GitHub User Followers List...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            githubUserListRecyclerView = findViewById(R.id.githubFollowersListRecyclerView);
            githubUserListLayoutManager = new LinearLayoutManager(this);
            githubUserListRecyclerView.setLayoutManager(githubUserListLayoutManager);
            githubUserListRecyclerView.smoothScrollToPosition(0);
        }
    }

//    Manage Activity Back Stack

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent loadTopStack = new Intent(UserFollowersActivity.this, DetailsActivity.class);
            loadTopStack.putExtra("username", username);
            loadTopStack.putExtra("profileLink", profileLink);
            loadTopStack.putExtra("token",token);
            startActivity(loadTopStack);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Retrofit API Call to read json from GitHubAPI

    private void loadJSON()
    {
        try{
            GitHubUserFollowersApi api = GitHubAPIClient.getClient(token).create(GitHubUserFollowersApi.class);
            Call<List<UsersList>> call = api.listUserFollowerDetails(getIntent().getStringExtra("username"));
            call.enqueue(new Callback<List<UsersList>>() {
                @Override
                public void onResponse(Call<List<UsersList>> call, Response<List<UsersList>> response) {
                    List<UsersList> items = response.body();
                    githubUserListAdapter = new GitHubSearchedUserFollowersAdapter(getApplicationContext(), items);
                    githubUserListRecyclerView.setAdapter(githubUserListAdapter);
                    githubUserListRecyclerView.smoothScrollToPosition(0);
                    progressDialog.hide();
                }

                @Override
                public void onFailure(Call<List<UsersList>> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    progressDialog.hide();
                }
            });
        }
        catch (Exception e) {
            Log.e(TAG, "Error: "+e.getMessage());
            progressDialog.hide();
        }
    }

    @Override
    public void onBackPressed() {
        Intent loadTopStack = new Intent(UserFollowersActivity.this, DetailsActivity.class);
        loadTopStack.putExtra("username", username);
        loadTopStack.putExtra("profileLink", profileLink);
        loadTopStack.putExtra("token",token);
        startActivity(loadTopStack);
        finish();
    }
}
