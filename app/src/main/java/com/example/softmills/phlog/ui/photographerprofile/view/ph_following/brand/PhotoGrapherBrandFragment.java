package com.example.softmills.phlog.ui.photographerprofile.view.ph_following.brand;

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
public class PhotoGrapherBrandFragment extends BaseFragment {

    private View mainView;


    public static PhotoGrapherBrandFragment getInstance() {
        return new PhotoGrapherBrandFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return  mainView=inflater.inflate(R.layout.fragment_photographer_follow_brand,container,false);
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
