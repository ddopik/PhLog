package com.example.softmills.phlog.ui.photographerprofile.editprofile.presenter;

import android.content.Context;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface EditPhotoGrapherProfileFragmentPresenter {

    void getProfileEditData();

    void updateProfile(Context context, String name, String username, String email, String password, String profile, String cover);

    void terminate();
}
