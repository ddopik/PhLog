package com.example.softmills.phlog.ui.brand.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.androidnetworking.error.ANError;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.base.commonmodel.BaseErrorResponse;
import com.example.softmills.phlog.base.commonmodel.ErrorMessageResponse;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.brand.view.BrandInnerActivityView;
import com.google.gson.Gson;

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
    public void getBrandInnerData(int brandId) {
        brandInnerActivityView.viewInnerBrandProgressBar(true);
        BaseNetworkApi.getBrandInnerData(PrefUtils.getUserToken(context),String.valueOf( brandId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(brandInnerResponse -> {
                    brandInnerActivityView.viewInnerBrandData(brandInnerResponse.brand);
                    brandInnerActivityView.viewInnerBrandProgressBar(false);
                }, throwable -> {
                    brandInnerActivityView.viewInnerBrandProgressBar(false);
                    if (throwable instanceof ANError) {
                        ANError error = (ANError) throwable;
                        if (error.getErrorCode() == BaseNetworkApi.STATUS_BAD_REQUEST) {
                            ErrorMessageResponse errorMessageResponse = new Gson().fromJson(error.getErrorBody(), ErrorMessageResponse.class);
                            for (BaseErrorResponse e : errorMessageResponse.errors) {
                                if (e.code.equals(BaseNetworkApi.ERROR_NOT_FOUND)) {
                                    brandInnerActivityView.showNotFoundDialog();
                                    break;
                                }
                            }
                        }
                    }
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void followBrand(String id) {
        BaseNetworkApi.followBrand(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {
                    brandInnerActivityView.viwBrandFollowedState(true);
//                    brandInnerActivityView.viewInnerBrandData(followBrandResponse.data.brand);

                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });



    }

    @SuppressLint("CheckResult")
    @Override
    public void unFollowBrand(String id) {
        BaseNetworkApi.unFollowBrand(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {
                    brandInnerActivityView.viwBrandFollowedState(false);
//                    brandInnerActivityView.viewInnerBrandData(followBrandResponse.data.brand);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }
}
