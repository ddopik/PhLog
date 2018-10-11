package com.example.softmills.phlog.ui.photographerprofile.view.ph_following.following.view;

import com.example.softmills.phlog.ui.photographerprofile.view.ph_following.following.model.PhotoGrapherFollowingInObj;

import java.util.List;

/**
 * Created by abdalla_maged on 10/11/2018.
 */
public interface PhotoGrapherFollowingInFragmentView {

    void viewPhotographerFollowingIn(List<PhotoGrapherFollowingInObj> photoGrapherFollowingInObjList);
    void viewPhotographerFollowingInProgress(boolean state);
    void viewMessage(String msg);
}
