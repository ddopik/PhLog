package com.example.softmills.phlog.ui.brand.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.brand.view.BrandInnerActivityView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 11/12/2018.
 */
public class BrandInnerDataPresenterImpl implements BrandInnerPresenter {

    private String TAG = BrandInnerDataPresenterImpl.class.getSimpleName();
    private Context context;
    private BrandInnerActivityView brandInnerActivityView;

    public BrandInnerDataPresenterImpl(Context context, BrandInnerActivityView brandInnerActivityView) {
        this.context = context;
        this.brandInnerActivityView = brandInnerActivityView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getBrandInnerData(String brandId) {
        brandInnerActivityView.viewInnerBrandProgressBar(true);
        BaseNetworkApi.getBrandInnerData(PrefUtils.getUserToken(context), brandId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(brandInnerResponse -> {
                    brandInnerActivityView.viewInnerBrandData(brandInnerResponse.brand);
                    brandInnerActivityView.viewInnerBrandProgressBar(false);
                }, throwable -> {
                    brandInnerActivityView.viewInnerBrandProgressBar(false);
                    ErrorUtils.setError(context, TAG, throwable);
                });

    }
}
