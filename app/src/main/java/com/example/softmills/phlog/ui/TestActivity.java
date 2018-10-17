package com.example.softmills.phlog.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.PagingController;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.model.PhotoGrapherFollowingObj;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.presenter.PhotoGrapherFollowingInPresenter;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.presenter.PhotoGrapherFollowingInPresenterImpl;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.view.PhotoGrapherFollowingAdapter;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.view.PhotoGrapherFollowingFragmentView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/15/2018.
 */
public class TestActivity extends BaseActivity implements PhotoGrapherFollowingFragmentView {



    private List<PhotoGrapherFollowingObj> photoGrapherFollowingObjs = new ArrayList<>();
    private PhotoGrapherFollowingAdapter photoGrapherFollowingAdapter;
    private View mainView;
    private CustomRecyclerView followingRV;
    private PhotoGrapherFollowingInPresenter photoGrapherFollowingInPresenter;
    private PagingController pagingController;
    private ProgressBar followingProgressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initPresenter();
        photoGrapherFollowingInPresenter.getPhotoGrapherFollowing(0);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void initPresenter() {
        photoGrapherFollowingInPresenter = new PhotoGrapherFollowingInPresenterImpl(getBaseContext(), this);
    }

    @Override
    public void initView() {
        followingProgressBar = findViewById(R.id.photographer_following_progress_bar);
        followingRV = findViewById(R.id.photographer_following_rv);
        photoGrapherFollowingAdapter = new PhotoGrapherFollowingAdapter(getBaseContext(), photoGrapherFollowingObjs);
        followingRV.setAdapter(photoGrapherFollowingAdapter);
    }

    @Override
    public void viewPhotographerFollowingIn(List<PhotoGrapherFollowingObj> photoGrapherFollowingObjList) {
        this.photoGrapherFollowingObjs.addAll(photoGrapherFollowingObjList);
        this.photoGrapherFollowingObjs.addAll(photoGrapherFollowingObjList);
        this.photoGrapherFollowingObjs.addAll(photoGrapherFollowingObjList);
        this.photoGrapherFollowingObjs.addAll(photoGrapherFollowingObjList);
        this.photoGrapherFollowingObjs.addAll(photoGrapherFollowingObjList);
        this.photoGrapherFollowingObjs.addAll(photoGrapherFollowingObjList);
        this.photoGrapherFollowingObjs.addAll(photoGrapherFollowingObjList);
        this.photoGrapherFollowingObjs.addAll(photoGrapherFollowingObjList);
        this.photoGrapherFollowingObjs.addAll(photoGrapherFollowingObjList);
        this.photoGrapherFollowingObjs.addAll(photoGrapherFollowingObjList);
        this.photoGrapherFollowingObjs.addAll(photoGrapherFollowingObjList);
        this.photoGrapherFollowingObjs.addAll(photoGrapherFollowingObjList);
        photoGrapherFollowingAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewPhotographerFollowingInProgress(boolean state) {
        if (state) {
            followingProgressBar.setVisibility(View.VISIBLE);
        } else {
            followingProgressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void viewMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void viewPhotographerFollowingSearch(List<PhotoGrapherFollowingObj> photoGrapherFollowingObjList) {

    }
}
