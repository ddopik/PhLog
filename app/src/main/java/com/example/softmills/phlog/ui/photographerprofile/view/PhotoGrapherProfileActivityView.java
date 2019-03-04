package com.example.softmills.phlog.ui.photographerprofile.view;

import com.example.softmills.phlog.base.commonmodel.Photographer;

public interface PhotoGrapherProfileActivityView {

    void showPhotoGrapherProfileData(Photographer photoGrapherProfileData);

    void showMessage(String msg);

    void showProfileProgress(boolean State);

    void UploadProfileImgFinished(Boolean state);

    void logoutSuccess();
}
