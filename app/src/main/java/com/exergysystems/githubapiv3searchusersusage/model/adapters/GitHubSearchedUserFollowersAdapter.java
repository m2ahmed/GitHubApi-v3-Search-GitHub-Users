package com.exergysystems.githubapiv3searchusersusage.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exergysystems.githubapiv3searchusersusage.R;
import com.exergysystems.githubapiv3searchusersusage.model.bean.UsersList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GitHubSearchedUserFollowersAdapter extends RecyclerView.Adapter<GitHubSearchedUserFollowersAdapter.ItemViewHolder>{

    private Context mContext;
    private List<UsersList> items;

    public GitHubSearchedUserFollowersAdapter(Context mContext, List<UsersList> items)
    {
        this.mContext = mContext;
        this.items = items;
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
    static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView userName;
        ImageView userImg;
        TextView profileLink;
        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.cover);
            userName = itemView.findViewById(R.id.username);
            profileLink = itemView.findViewById(R.id.githubLink);
        }
    }
}
