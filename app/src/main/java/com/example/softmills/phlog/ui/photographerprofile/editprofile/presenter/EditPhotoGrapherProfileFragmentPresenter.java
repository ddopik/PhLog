package com.example.softmills.phlog.ui.photographerprofile.editprofile.presenter;

import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.ui.photographerprofile.model.ProfilePhotoGrapherInfoResponse;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface EditPhotoGrapherProfileFragmentPresenter {

    void getProfileEditData();

    void updateProfile(String name, String username, String email, String password, String profile, String cover);
}
