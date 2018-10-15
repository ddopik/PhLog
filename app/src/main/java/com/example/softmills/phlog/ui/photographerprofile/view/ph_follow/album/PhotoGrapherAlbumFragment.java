package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.album;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;

/**
 * Created by abdalla_maged on 10/14/2018.
 */
public class PhotoGrapherAlbumFragment extends BaseFragment {

    private View mainView;

    public static PhotoGrapherAlbumFragment getInstance() {
        return new PhotoGrapherAlbumFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return mainView = inflater.inflate(R.layout.fragment_photographer_follow_album, container, false);
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
}
