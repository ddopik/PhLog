package com.example.softmills.phlog.ui.uploadimage.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.base.commonmodel.Tags;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.uploadimage.view.AddTagActivityView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public class AddTagActivityPresenterImpl implements AddTagActivityPresenter {
    private String TAG=AddTagActivityPresenterImpl.class.getSimpleName();
    private Context context;
    private AddTagActivityView addTagActivityView;

    public AddTagActivityPresenterImpl(Context context, AddTagActivityView addTagActivityView) {

        this.context = context;
        this.addTagActivityView = addTagActivityView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void uploadPhoto(String imagePath, String imageCaption, String location, String draftState, HashMap<String, String> imageType, List<Tags> tagsList) {
        addTagActivityView.viewUploadProgress(true);
        HashMap<String, String> tagsSelected = new HashMap<String, String>();
        for (int i = 0; i < tagsList.size(); i++) {
            tagsSelected.put("tags[" + i + "]", tagsList.get(i).name);
        }

        BaseNetworkApi.uploadPhotoGrapherPhoto(PrefUtils.getUserToken(context), imageCaption, location, new File(imagePath), tagsSelected)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( uploadImgResponse -> {
                    addTagActivityView.viewUploadProgress(false);
                    addTagActivityView.viewMessage(context.getResources().getString(R.string.photo_uploaded));
                },throwable -> {
                    ErrorUtils.setError(context, TAG, throwable.toString());
                    addTagActivityView.viewUploadProgress(false);
                });
    }


    private void uploadPhotoGrapherPhoto() {

    }

}
