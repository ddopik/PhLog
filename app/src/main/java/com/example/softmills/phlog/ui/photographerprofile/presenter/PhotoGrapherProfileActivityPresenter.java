package com.example.softmills.phlog.ui.photographerprofile.presenter;

import android.content.Context;

import java.io.File;

import io.reactivex.Observable;

public interface PhotoGrapherProfileActivityPresenter {
    void getPhotoGrapherProfileData();
    void uploadPhoto(File imagePath);

    void logout(Context context);

    Observable<Boolean> logout();
}
