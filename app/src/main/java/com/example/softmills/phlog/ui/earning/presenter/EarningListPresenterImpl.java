package com.example.softmills.phlog.ui.earning.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.earning.view.EarningListFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class EarningListPresenterImpl implements EarningListPresenter {

    private String TAG=EarningListPresenterImpl.class.getSimpleName();
    private EarningListFragmentView earningListFragmentView;
    private Context context;

    public EarningListPresenterImpl(EarningListFragmentView earningListFragmentView, Context context) {
        this.earningListFragmentView = earningListFragmentView;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getEarningList(String page) {
        earningListFragmentView.viewEaringListProgress(true);
        BaseNetworkApi.geEarning(PrefUtils.getUserToken(context), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(earningResponse -> {
                    if (earningResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
//                        earningListFragmentView.viewEaringListProgress(earningResponse.earningLis);
                    } else {
//                        ErrorUtils.setError(context, TAG, earningListFragmentView.msg, earningListFragmentView.state);
                    }
                    earningListFragmentView.viewEaringListProgress(false);
                }, throwable -> {
                    earningListFragmentView.viewEaringListProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    // ErrorUtils.setError(context, TAG, albumPreviewResponse.msg, albumPreviewResponse.state);
// ErrorUtils.setError(context, TAG, throwable.toString());
}
