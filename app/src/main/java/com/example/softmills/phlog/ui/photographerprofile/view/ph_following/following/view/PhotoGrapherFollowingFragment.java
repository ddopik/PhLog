package com.example.softmills.phlog.ui.photographerprofile.view.ph_following.following.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_following.following.model.PhotoGrapherFollowingObj;

import java.util.List;

/**
 * Created by abdalla_maged on 10/11/2018.
 */
public class PhotoGrapherFollowingFragment extends BaseFragment {


    private List<PhotoGrapherFollowingObj> photoGrapherFollowingObjs;

    public static PhotoGrapherFollowingFragment getInstance(){
        return new PhotoGrapherFollowingFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {

    }

    private void initListner(){

    }
}
