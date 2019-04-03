package com.example.softmills.phlog.ui.allphotos.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.allphotos.view.AllPhotographerPhotosActivity;
import com.example.softmills.phlog.ui.allphotos.view.AllPhotographerPhotosActivityView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class AllPhotographerPhotosPresenterImpl implements AllPhotographerPhotosPresenter {
    private static String TAG = AllPhotographerPhotosPresenterImpl.class.getSimpleName();
    private AllPhotographerPhotosActivityView allPhotographerPhotosActivityView;
    private Context context;

    public AllPhotographerPhotosPresenterImpl(Context context, AllPhotographerPhotosActivityView allPhotographerPhotosActivityView) {
        this.allPhotographerPhotosActivityView = allPhotographerPhotosActivityView;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getPhotographerPhotos(int Page) {
        allPhotographerPhotosActivityView.showImageListProgress(true);
        BaseNetworkApi.getPhotoGrapherPhotos(Page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photoGrapherPhotosResponse -> {
                    allPhotographerPhotosActivityView.showImageListProgress(false);
                    allPhotographerPhotosActivityView.showPhotosList(photoGrapherPhotosResponse.data.data);
                    if (photoGrapherPhotosResponse.data.nextPageUrl != null) {
                        allPhotographerPhotosActivityView.setNextPageUrl(Utilities.getNextPageNumber(context, photoGrapherPhotosResponse.data.nextPageUrl));

                    } else {
                        allPhotographerPhotosActivityView.setNextPageUrl(null);
                    }
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    allPhotographerPhotosActivityView.showImageListProgress(false);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void uploadCampaignExistingPhoto(String campaignId, String imageId) {

        allPhotographerPhotosActivityView.showImageListProgress(true);
        Map<String, String> data = new HashMap<String, String>();
        data.put("campaign_id", campaignId);
        data.put("photo_id", imageId);
        BaseNetworkApi.uploadCampaignExistingPhoto(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uploadImgResponse -> {
                    allPhotographerPhotosActivityView.showImageListProgress(false);
                    Toast.makeText(context, context.getResources().getString(R.string.photo_uploaded), Toast.LENGTH_SHORT).show();
                    ((AllPhotographerPhotosActivity) context).finish();
                }, throwable -> {
                    allPhotographerPhotosActivityView.showImageListProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }
}
