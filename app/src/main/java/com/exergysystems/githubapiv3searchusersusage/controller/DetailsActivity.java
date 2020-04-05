package com.exergysystems.githubapiv3searchusersusage.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.exergysystems.githubapiv3searchusersusage.R;
import com.exergysystems.githubapiv3searchusersusage.api.module.GitHubAPIClient;
import com.exergysystems.githubapiv3searchusersusage.api.interfaces.GitHubUserDetailsApi;
import com.exergysystems.githubapiv3searchusersusage.model.bean.UserDetails;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "MyTag";
    ImageView userImage;
    TextView name,company,repos,followers,following,location,email;

    //Loading Dialog
    ProgressDialog progressDialog;

    LinearLayout nameContainer,emailContainer,companyContainer,reposContainer,followersContainer,followingContainer,locationContainer;

    private String gitHubProfileLink = "";

    String username = "";
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Called");
        setContentView(R.layout.activity_details);

        initViews();

        Intent i = getIntent();
        if(i!=null) {
            username = i.getStringExtra("username");
            token = i.getStringExtra("token");
        }
        readJsonObject();
    }

    private void initViews()
    {
        userImage = findViewById(R.id.userImage);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        company = findViewById(R.id.company);
        repos = findViewById(R.id.repos);
        followers = findViewById(R.id.followers);
        following = findViewById(R.id.following);
        location = findViewById(R.id.location);

        nameContainer = findViewById(R.id.nameContainer);
        emailContainer = findViewById(R.id.emailContainer);
        companyContainer = findViewById(R.id.companyContainer);
        reposContainer = findViewById(R.id.reposContainer);
        followersContainer = findViewById(R.id.followersContainer);
        followingContainer = findViewById(R.id.followingContainer);
        locationContainer = findViewById(R.id.locationContainer);
        //Display Action Bar with Backpress Arrow
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detailed User Information");
        }catch (Exception e) {
            Log.e(TAG, "ActionBar: "+e.getMessage() );
        }
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Fetching GitHub User Information...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    //Managing Activity BackStack

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Retrofit API Call to read json from GitHubAPI

    private void readJsonObject()
    {
        try {
            GitHubUserDetailsApi gitHubUserDetailsApi = GitHubAPIClient.getClient(token).create(GitHubUserDetailsApi.class);
            Call<UserDetails> call = gitHubUserDetailsApi.listUserDetails(getIntent().getStringExtra("username"));
            call.enqueue(new Callback<UserDetails>() {
                @Override
                public void onResponse(@NotNull Call<UserDetails> call,@NotNull Response<UserDetails> response) {
                    Log.d(TAG, "GitHub Profile: "+response.body().getHtml_url());
                    gitHubProfileLink = response.body().getHtml_url();
                    Log.d(TAG, "ImageLink: "+response.body().getAvatar_url());
                    Glide.with(DetailsActivity.this).load(response.body().getAvatar_url()).placeholder(R.drawable.load).into(userImage);

                    Log.d(TAG, "Name: "+response.body().getName());
                    if(response.body().getName()!=null) {
                        nameContainer.setVisibility(View.VISIBLE);
                        name.setText(response.body().getName());
                    }
                    Log.d(TAG, "Email: "+response.body().getEmail());
                    if(response.body().getEmail()!=null) {
                        emailContainer.setVisibility(View.VISIBLE);
                        email.setText(response.body().getEmail());
                    }
                    Log.d(TAG, "Company: "+response.body().getCompanyName());
                    if(response.body().getCompanyName()!=null) {
                        companyContainer.setVisibility(View.VISIBLE);
                        company.setText(response.body().getCompanyName());
                    }
                    Log.d(TAG, "Public Repositories: "+response.body().getPublicRepositories());
                    if(response.body().getPublicRepositories()!=null) {
                        reposContainer.setVisibility(View.VISIBLE);
                        repos.setText(response.body().getPublicRepositories());
                    }
                    Log.d(TAG, "Follower: "+response.body().getFollowersNumber());
                    if(response.body().getFollowersNumber()!=null) {
                        followersContainer.setVisibility(View.VISIBLE);
                        followers.setText(response.body().getFollowersNumber());
                    }
                    Log.d(TAG, "Following: "+response.body().getFollowing());
                    if(response.body().getFollowing()!=null) {
                        followingContainer.setVisibility(View.VISIBLE);
                        following.setText(response.body().getFollowing());
                    }
                    Log.d(TAG, "Location: "+response.body().getLocation());
                    if(response.body().getLocation()!=null) {
                        locationContainer.setVisibility(View.VISIBLE);
                        location.setText(response.body().getLocation());
                    }
                    progressDialog.hide();
                }

                @Override
                public void onFailure(Call<UserDetails> call,Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    progressDialog.hide();
                }
            });
        }catch (Exception e) {
            Log.e(TAG, "onFailure: Catch Exception " + e.getMessage());
            progressDialog.hide();
        }
    }

    //Share Developer Profile

    private Intent createShareForecastIntent()
    {
        String username = getIntent().getStringExtra("username");
        String link = getIntent().getStringExtra("profileLink");
        return ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText("Check out this awesome developer @"+username+", "+link)
                .getIntent();
    }

    // Set Menu Item

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail,menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForecastIntent());
        return true;
    }

    //Hide Loading Dialog

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Called");
        if(progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    //Open GitHub Profile through Browser

    public void goToGitHubProfile(View view) {
        if(!gitHubProfileLink.equals("")) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(gitHubProfileLink));
            startActivity(i);
        }
    }

    //Open User Followers Activity

    public void seeUserFollowersList(View view) {
        Intent i = new Intent(DetailsActivity.this,UserFollowersActivity.class);
        i.putExtra("token",token);
        i.putExtra("username",username);
        i.putExtra("profileLink",gitHubProfileLink);
        startActivity(i);
        finish();
    }



}
