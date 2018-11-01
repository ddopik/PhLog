package com.example.softmills.phlog.ui.search.view.profile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.search.view.profile.view.ProfileSearchFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 11/1/2018.
 */
public class ProdileSearchPresenterImpl implements ProdileSearchPresenter {

    private String TAG = ProdileSearchPresenterImpl.class.getSimpleName();

    private Context context;
    private ProfileSearchFragmentView profileSearchFragmentView;

    public ProdileSearchPresenterImpl(Context context, ProfileSearchFragmentView profileSearchFragmentView) {
        this.context = context;
        this.profileSearchFragmentView = profileSearchFragmentView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getProfileSearchList(String key, int page) {
        profileSearchFragmentView.viewProfileSearchProgress(true);
        BaseNetworkApi.getProfileSearch(PrefUtils.getUserToken(context), key, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profileSearchResponse -> {
                    if (profileSearchResponse.data != null) {
                        profileSearchFragmentView.viewProfileSearchProgress(false);
                        profileSearchFragmentView.viewProfileSearchItems(profileSearchResponse.data.data);
                    } else {
                        profileSearchFragmentView.viewProfileSearchProgress(false);
                        Log.e(TAG, "getProfileSearchList() ---> Error " + profileSearchResponse.toString());

                        profileSearchFragmentView.showMessage(" getProfileSearchList ()--->error");
                    }

                }, throwable -> {
                    profileSearchFragmentView.viewProfileSearchProgress(false);
                    Log.e(TAG, "getProfileSearchList() ---> Error " + throwable.getMessage());
                });
    }
}
