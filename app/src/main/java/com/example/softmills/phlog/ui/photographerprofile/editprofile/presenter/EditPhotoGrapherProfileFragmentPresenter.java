package com.example.softmills.phlog.ui.photographerprofile.editprofile.presenter;

import android.content.Context;

import com.example.softmills.phlog.ui.signup.model.Country;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface EditPhotoGrapherProfileFragmentPresenter {

    void getProfileEditData();

    void updateProfile(Context context, String name, String email, String phone, Integer countryId, String profile, String cover, String oldPassword, String newPassword);

    void terminate();

    void getAllCountries(Consumer<List<Country>> consumer);

    void changePassword(Context context, String oldPassword, String newPassword);
}
