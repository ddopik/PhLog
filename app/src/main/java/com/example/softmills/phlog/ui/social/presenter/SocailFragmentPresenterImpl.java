package com.example.softmills.phlog.ui.social.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.social.view.SocialFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SocailFragmentPresenterImpl implements SocialFragmentPresenter {

    private static String TAG = SocailFragmentPresenterImpl.class.getSimpleName();

    private Context context;
    private SocialFragmentView socialFragmentView;

    public SocailFragmentPresenterImpl(Context context, SocialFragmentView socialFragmentView) {
        this.context = context;
        this.socialFragmentView = socialFragmentView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getSocialData() {
        socialFragmentView.viewSocialDataProgress(true);
        BaseNetworkApi.getSocialData(PrefUtils.getUserToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialResponse -> {
                    if (socialResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        socialFragmentView.viewSocialData(socialResponse.data);
                    } else {
                        ErrorUtils.setError(context, TAG, socialResponse.msg, socialResponse.state);
                    }
                    socialFragmentView.viewSocialDataProgress(false);
                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable.getMessage());
                    socialFragmentView.viewSocialDataProgress(false);
                });

    }
}
