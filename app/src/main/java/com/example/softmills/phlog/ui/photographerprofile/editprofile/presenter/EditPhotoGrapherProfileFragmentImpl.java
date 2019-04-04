package com.example.softmills.phlog.ui.photographerprofile.editprofile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.editprofile.view.EditPhotoGrapherProfileFragmentView;
import com.example.softmills.phlog.ui.signup.model.Country;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
        this.context = context;
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
    public void updateProfile(Context context, String name, String email, String phone, Integer countryId, String profile, String cover, String oldPassword, String newPassword) {
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
        data.put("email", email);
        data.put("mobile", phone);
        if (oldPassword != null) {
            data.put("old_password", oldPassword);
        }
        if (newPassword != null) {
            data.put("password", newPassword);
        }
        if (countryId != null)
            data.put("country_id", countryId.toString());

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

    @Override
    public void getAllCountries(Consumer<List<Country>> consumer) {
        Disposable d = BaseNetworkApi.getAllCounters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allCountersRepose -> {consumer.accept(allCountersRepose.countries);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
        disposables.add(d);
    }

    @Override
    public void changePassword(Context context, String oldPassword, String newPassword) {
        view.viewEditProfileProgress(true);
        HashMap<String, String> data = new HashMap<>();
        if (newPassword != null) {
            data.put("password", newPassword);
        }
        if (oldPassword != null) {
            data.put("old_password", oldPassword);
        }

        Disposable disposable = BaseNetworkApi.updateProfile(null, data, PrefUtils.getUserToken(this.context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> view.viewEditProfileProgress(false))
                .subscribe(s -> {
                    view.viewEditProfileProgress(false);
                    if (s != null) {
                        view.showMessage(R.string.password_changed);
                    }
                }, throwable -> {
                    view.viewEditProfileProgress(false);
                    if (this.context != null)
                        ErrorUtils.Companion.setError(this.context, TAG, throwable);
                });
        disposables.add(disposable);
    }
}
