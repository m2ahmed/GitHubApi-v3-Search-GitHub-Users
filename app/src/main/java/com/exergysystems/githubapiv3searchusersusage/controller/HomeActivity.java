package com.exergysystems.githubapiv3searchusersusage.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.exergysystems.githubapiv3searchusersusage.api.interfaces.RetrieveTokenAPI;
import com.exergysystems.githubapiv3searchusersusage.api.module.ApiAccessTokenClient;
import com.exergysystems.githubapiv3searchusersusage.model.adapters.GitHubSearchedUsersAdapter;
import com.exergysystems.githubapiv3searchusersusage.R;
import com.exergysystems.githubapiv3searchusersusage.api.module.GitHubAPIClient;
import com.exergysystems.githubapiv3searchusersusage.api.interfaces.GitHubSearchUsersApi;

import com.exergysystems.githubapiv3searchusersusage.model.bean.ApiDetails;
import com.exergysystems.githubapiv3searchusersusage.model.bean.UsersList;
import com.exergysystems.githubapiv3searchusersusage.model.implementations.UsersSearchedListResponseModelImpl;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    String token;


    //Update GitHub API Access Token

    private static final String TAG = "MyTag";
    private RecyclerView githubUserListRecyclerView;
    private RecyclerView.Adapter githubUserListAdapter;
    private RecyclerView.LayoutManager githubUserListLayoutManager;
    //Loading Dialog
    ProgressDialog progressDialog;

    private EditText gitHubUserSearchBox;

    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    TextView info2,info3;
    ImageView info1;

    String query = "";

    ProgressDialog apiFetching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        readApiDetails();
        initViews();
    }

    //Get Api Access Token
    private void readApiDetails()
    {
        try {
            RetrieveTokenAPI apiAccessClient = ApiAccessTokenClient.getClient().create(RetrieveTokenAPI.class);
            Call<ApiDetails> call = apiAccessClient.listApiDetails();
            call.enqueue(new Callback<ApiDetails>() {
                @Override
                public void onResponse(@NotNull Call<ApiDetails> call, @NotNull Response<ApiDetails> response) {
                    Log.d(TAG, "Api Access Token: "+response.body().getApiAccessToken());
                    token = response.body().getApiAccessToken();
                    Toast.makeText(HomeActivity.this,"API Access Token Received",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ApiDetails> call,Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });
        }catch (Exception e) {
            Log.e(TAG, "onFailure: Catch Exception " + e.getMessage());
        }
    }

    //Initialize Views

    private void initViews()
    {
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Fetching GitHub Users List...");
        progressDialog.setCancelable(false);

        info1 = findViewById(R.id.ms_info1);
        info2 = findViewById(R.id.ms_info2);
        info3 = findViewById(R.id.ms_info3);

        gitHubUserSearchBox = findViewById(R.id.et_searchBox);
//        gitHubUserSearchBox.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
//                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
//                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    View view = getCurrentFocus();
//                    if (view != null) {
//                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });

        //---------------AutoSearch-----------------------------//

        final Handler handler = new Handler();
        final Runnable input_finish_checker = new Runnable() {
            public void run() {
                if (System.currentTimeMillis() > ((last_text_edit + delay) - 800)) {
                    progressDialog.show();
                    query = gitHubUserSearchBox.getText().toString();
                    loadJSON();
                    info1.setVisibility(View.GONE);
                    info2.setVisibility(View.GONE);
                    info3.setVisibility(View.GONE);
                }
            }
        };

        //AutoText Pickup in Search Box

        gitHubUserSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {

                        if (isNetworkAvailable()) {
                            if(token!=null) {
                                last_text_edit = System.currentTimeMillis();
                                handler.postDelayed(input_finish_checker, delay);
                            }else {
                                readApiDetails();
                            }
                        } else {
                            info1.setImageResource(R.drawable.ic_no_internet_connection);
                            info2.setText(getResources().getString(R.string.no_internet_connection));
                            info3.setVisibility(View.GONE);
                        }
                }
            }
        });

        githubUserListRecyclerView = findViewById(R.id.githubUserListRecyclerView);
        githubUserListLayoutManager = new LinearLayoutManager(this);
        githubUserListRecyclerView.setLayoutManager(githubUserListLayoutManager);
        githubUserListRecyclerView.smoothScrollToPosition(0);
    }

    //Retrofit API Call to read json from GitHubAPI

    private void loadJSON()
    {
        try{
            if(token!=null)
            {
            GitHubAPIClient gitHubAPIClient = new GitHubAPIClient();
            GitHubSearchUsersApi apiService = GitHubAPIClient.getClient(token).create(GitHubSearchUsersApi.class);
            Call<UsersSearchedListResponseModelImpl> call = apiService.getSearchedUsers(query);
            call.enqueue(new Callback<UsersSearchedListResponseModelImpl>() {
                @Override
                public void onResponse(Call<UsersSearchedListResponseModelImpl> call, Response<UsersSearchedListResponseModelImpl> response) {
                    List<UsersList> items = response.body().getUserListItems();
                    if(items.size()>0) {
                        githubUserListAdapter = new GitHubSearchedUsersAdapter(getApplicationContext(), items,token);
                        githubUserListRecyclerView.setAdapter(githubUserListAdapter);
                        githubUserListRecyclerView.smoothScrollToPosition(0);
                        progressDialog.hide();
                        hideKeyboard(HomeActivity.this);
                    }
                    // No username found from the searched username
                    else {
                        new AlertDialog.Builder(HomeActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("User Not Found")
                                .setMessage("Sorry! No user found for username "+query+" :|")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .show();
                        progressDialog.hide();
                        hideKeyboard(HomeActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<UsersSearchedListResponseModelImpl> call, Throwable t) {
                    Log.d(TAG, "Error!"+t.getMessage());
                    Toast.makeText(HomeActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    hideKeyboard(HomeActivity.this);
                }
            });
            }
            else {
                progressDialog.hide();
                new AlertDialog.Builder(HomeActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Unable to get API")
                        .setMessage("Sorry! No GitHub API Found :|")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                progressDialog.hide();
                hideKeyboard(HomeActivity.this);
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error: "+e.getMessage());
            progressDialog.hide();
        }
    }

    //Check Internet Internet Availability

    public boolean isNetworkAvailable() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        return connected;
    }

    private void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
