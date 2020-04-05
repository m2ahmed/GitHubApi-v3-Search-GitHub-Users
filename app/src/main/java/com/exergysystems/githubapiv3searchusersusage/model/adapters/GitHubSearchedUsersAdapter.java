package com.exergysystems.githubapiv3searchusersusage.model.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exergysystems.githubapiv3searchusersusage.R;
import com.exergysystems.githubapiv3searchusersusage.controller.DetailsActivity;
import com.exergysystems.githubapiv3searchusersusage.model.bean.UsersList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GitHubSearchedUsersAdapter extends RecyclerView.Adapter<GitHubSearchedUsersAdapter.ItemViewHolder>{

    private Context mContext;
    private List<UsersList> items;

    String token;

    public GitHubSearchedUsersAdapter(Context mContext, List<UsersList> items,String token) {
        this.mContext = mContext;
        this.items = items;
        this.token = token;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user,parent,false);
       return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Picasso.get().load(items.get(position).getProfileImgURL()).placeholder(R.drawable.load).into(holder.userImg);
        holder.userName.setText(items.get(position).getLogin());
        holder.profileLink.setText(items.get(position).getGitHubProfileURL());
    }
    class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView userName;
        ImageView userImg;
        TextView profileLink;
        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.cover);
            userName = itemView.findViewById(R.id.username);
            profileLink = itemView.findViewById(R.id.githubLink);
            //On Click Listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION) {
                        UsersList clickedDataItem = items.get(pos);
                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        intent.putExtra("token",token);
                        intent.putExtra("username",clickedDataItem.getLogin());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
