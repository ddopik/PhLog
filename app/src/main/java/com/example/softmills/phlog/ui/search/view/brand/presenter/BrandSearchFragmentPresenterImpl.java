package com.example.softmills.phlog.ui.search.view.brand.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.search.view.brand.view.BrandSearchFragmentView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class BrandSearchFragmentPresenterImpl implements BrandSearchFragmentPresenter {


    private String TAG = BrandSearchFragmentPresenterImpl.class.getSimpleName();

    private Context context;
    private BrandSearchFragmentView brandSearchFragmentView;


    public BrandSearchFragmentPresenterImpl(Context context, BrandSearchFragmentView brandSearchFragmentView) {
        this.context = context;
        this.brandSearchFragmentView = brandSearchFragmentView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getSearchBrand(String key, int page) {
        brandSearchFragmentView.viewBrandSearchProgress(true);
        BaseNetworkApi.getBrandSearch(PrefUtils.getUserToken(context), key, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(brandSearchResponse -> {
                    Log.e(TAG, "getSearchBrand() ---> new request" );

                    brandSearchFragmentView.viewBrandSearchProgress(false);
                    if (brandSearchResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        brandSearchFragmentView.viewBrandSearchItems(brandSearchResponse.data.data);
                    } else {
                        brandSearchFragmentView.showMessage(brandSearchResponse.msg);
                        Log.e(TAG, "getSearchBrand() --->Error " + brandSearchResponse.toString());
                    }
                }, throwable -> {
                    brandSearchFragmentView.viewBrandSearchProgress(false);
                    Log.e(TAG, "getSearchBrand() Error--->" + throwable.getMessage());
                });

    }

}
