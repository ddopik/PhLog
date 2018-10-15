package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.PagingController;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.model.PhotoGrapherFollowingObj;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.presenter.PhotoGrapherFollowingInPresenter;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.presenter.PhotoGrapherFollowingInPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/11/2018.
 */
public class PhotoGrapherFollowingFragment extends BaseFragment implements PhotoGrapherFollowingFragmentView {


    private List<PhotoGrapherFollowingObj> photoGrapherFollowingObjs = new ArrayList<PhotoGrapherFollowingObj>();
    private PhotoGrapherFollowingAdapter photoGrapherFollowingAdapter;
    private View mainView;
    private CustomRecyclerView followingRV;
    private PhotoGrapherFollowingInPresenter photoGrapherFollowingInPresenter;
    private PagingController pagingController;
    private ProgressBar followingProgressBar;

    public static PhotoGrapherFollowingFragment getInstance() {
        return new PhotoGrapherFollowingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return mainView = inflater.inflate(R.layout.fragment_photographer_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        photoGrapherFollowingInPresenter.getPhotoGrapherFollowing(0);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    protected void initPresenter() {
        photoGrapherFollowingInPresenter = new PhotoGrapherFollowingInPresenterImpl(getContext(), this);
    }

    @Override
    protected void initViews() {
        followingProgressBar = mainView.findViewById(R.id.photographer_following_progress_bar);
        followingRV = mainView.findViewById(R.id.photographer_following_rv);
        photoGrapherFollowingAdapter = new PhotoGrapherFollowingAdapter(getContext(), photoGrapherFollowingObjs);
        followingRV.setAdapter(photoGrapherFollowingAdapter);
    }

    private void initListener() {
        pagingController = new PagingController(followingRV) {
            @Override
            public void getPagingControllerCallBack(int page) {
                photoGrapherFollowingInPresenter.getPhotoGrapherFollowing(page + 1);
            }
        };
    }

    @Override
    public void viewPhotographerFollowingIn(List<PhotoGrapherFollowingObj> photoGrapherFollowingObjList) {
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
}
