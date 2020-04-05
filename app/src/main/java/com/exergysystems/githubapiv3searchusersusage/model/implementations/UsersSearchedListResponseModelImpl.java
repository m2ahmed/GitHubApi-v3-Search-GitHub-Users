package com.exergysystems.githubapiv3searchusersusage.model.implementations;

import com.exergysystems.githubapiv3searchusersusage.model.bean.UsersList;
import com.exergysystems.githubapiv3searchusersusage.model.interfaces.UsersSearchedListResponseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersSearchedListResponseModelImpl implements UsersSearchedListResponseModel {

    //Get Array named items through this Serialization

    @SerializedName("items")
    @Expose
    private List<UsersList> items;

    public List<UsersList> getUserListItems() {
        return items;
    }

    public void setItems(List<UsersList> items) {
        this.items = items;
    }
}
