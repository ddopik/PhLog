package com.example.softmills.phlog.ui.photographerprofile.editprofile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.editprofile.view.EditPhotoGrapherProfileFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class EditPhotoGrapherProfileFragmentImpl implements EditPhotoGrapherProfileFragmentPresenter {

    private final String TAG = EditPhotoGrapherProfileFragmentImpl.class.getSimpleName();
    private EditPhotoGrapherProfileFragmentView editPhotoGrapherProfileFragmentView;
    private Context context;

    public EditPhotoGrapherProfileFragmentImpl(EditPhotoGrapherProfileFragmentView editPhotoGrapherProfileFragmentView, Context context) {
        this.editPhotoGrapherProfileFragmentView = editPhotoGrapherProfileFragmentView;
    }


    @SuppressLint("CheckResult")
    @Override
    public void getProfileEditData() {
        BaseNetworkApi.getProfileInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profilePhotoGrapherInfoResponse -> {
                    editPhotoGrapherProfileFragmentView.showPhotoGrapherProfileData(profilePhotoGrapherInfoResponse.data);
                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable);
                });
    }

    @Override
    public void editProfileData() {

    }
}
