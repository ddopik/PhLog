package com.example.softmills.phlog.ui.photographerprofile.editprofile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.editprofile.view.EditPhotoGrapherProfileFragmentView;

import java.io.File;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class EditPhotoGrapherProfileFragmentImpl implements EditPhotoGrapherProfileFragmentPresenter {

    private final String TAG = EditPhotoGrapherProfileFragmentImpl.class.getSimpleName();
    private EditPhotoGrapherProfileFragmentView view;
    private Context context;
    private CompositeDisposable disposables = new CompositeDisposable();

    public EditPhotoGrapherProfileFragmentImpl(EditPhotoGrapherProfileFragmentView editPhotoGrapherProfileFragmentView, Context context) {
        this.view = editPhotoGrapherProfileFragmentView;
    }


    @SuppressLint("CheckResult")
    @Override
    public void getProfileEditData() {
        BaseNetworkApi.getProfileInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profilePhotoGrapherInfoResponse -> {
                    view.showPhotoGrapherProfileData(profilePhotoGrapherInfoResponse.data);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    @Override
    public void updateProfile(Context context, String name, String username, String email
            , String password, String profile, String cover) {
        view.viewEditProfileProgress(true);
        HashMap<String, File> files = null;
        if (profile != null) {
            files = new HashMap<>();
            files.put("image_profile", new File(profile));
        }
        if (cover != null) {
            if (files == null)
                files = new HashMap<>();
            files.put("image_cover", new File(cover));
        }
        HashMap<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("username", username);
        data.put("email", email);
        data.put("password", password);
        Disposable disposable = BaseNetworkApi.updateProfile(files, data, PrefUtils.getUserToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    view.viewEditProfileProgress(false);
                    if (s != null) {
                        view.showMessage(R.string.profile_updated_successfully);
                    } else {
                        view.showMessage(R.string.profile_update_failed);
                    }
                }, throwable -> {
                    view.viewEditProfileProgress(false);
                    view.showMessage(R.string.profile_update_failed);
                    if (this.context != null)
                    ErrorUtils.Companion.setError(this.context, TAG, throwable);
                });
        disposables.add(disposable);
    }

    @Override
    public void terminate() {
        disposables.clear();
    }
}
