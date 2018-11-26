package com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseFragment;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class EditPhotoGrapherProfileFragment extends BaseFragment {
    private View mainView;



    public static EditPhotoGrapherProfileFragment getInstance(){
        EditPhotoGrapherProfileFragment editPhotoGrapherProfileFragment=new EditPhotoGrapherProfileFragment();

        return editPhotoGrapherProfileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_edit_photo_grapher_profile, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {

    }
}
