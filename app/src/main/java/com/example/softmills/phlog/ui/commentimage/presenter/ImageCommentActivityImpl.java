package com.example.softmills.phlog.ui.commentimage.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.androidnetworking.error.ANError;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.base.commonmodel.BaseErrorResponse;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.ErrorMessageResponse;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.commentimage.view.ImageCommentActivityView;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class ImageCommentActivityImpl implements ImageCommentActivityPresenter {

    private String TAG = ImageCommentActivityImpl.class.getSimpleName();
    private Context context;
    private ImageCommentActivityView imageCommentActivityView;

    public ImageCommentActivityImpl(Context context, ImageCommentActivityView imageCommentActivityView) {
        this.context = context;
        this.imageCommentActivityView = imageCommentActivityView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getImageDetails(int imageId) {

        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.getPhotoGrapherPhotoDetails(String.valueOf(imageId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageDetailsResponse -> {
                    imageCommentActivityView.viewImageDetails(imageDetailsResponse.data);
                    imageCommentActivityView.viewImageProgress(false);

                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);



                    /////////
                    if (throwable instanceof ANError) {
                        ANError error = (ANError) throwable;
                        if (error.getErrorCode() == BaseNetworkApi.STATUS_BAD_REQUEST) {
                            ErrorMessageResponse errorMessageResponse = new Gson().fromJson(error.getErrorBody(), ErrorMessageResponse.class);
                            for (BaseErrorResponse e : errorMessageResponse.errors) {
                                if (e.code.equals(BaseNetworkApi.ERROR_NOT_FOUND)) {
                                    imageCommentActivityView.onImageFailed();
                                     break;
                                }
                            }
                        }
                    }



                    //////////

                    imageCommentActivityView.viewImageProgress(false);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getImageComments(String imageId, String page) {
        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.getImageComments(imageId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumImgCommentResponse -> {
                    imageCommentActivityView.viewPhotoComment(albumImgCommentResponse.data);
                    imageCommentActivityView.viewImageProgress(false);
                    if (albumImgCommentResponse.data.nextPageUrl != null) {
                        imageCommentActivityView.setNextPageUrl(Utilities.getNextPageNumber(context, albumImgCommentResponse.data.nextPageUrl));

                    } else {
                        imageCommentActivityView.setNextPageUrl(null);
                    }

                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    imageCommentActivityView.viewImageProgress(false);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void submitComment(String imageId, String comment) {
        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.submitImageComment(imageId, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(submitImageCommentResponse -> {
                    imageCommentActivityView.onImageCommented(submitImageCommentResponse.data);
                    imageCommentActivityView.viewImageProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    imageCommentActivityView.viewImageProgress(false);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public Observable<Boolean> likePhoto(BaseImage baseImage) {
        imageCommentActivityView.viewImageProgress(true);
        return BaseNetworkApi.likeImage(String.valueOf(baseImage.id))
                .map(likeImageResponse -> likeImageResponse != null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });


    }

    @SuppressLint("CheckResult")
    @Override
    public Observable<Boolean> unLikePhoto(BaseImage baseImage) {
        imageCommentActivityView.viewImageProgress(true);
        return BaseNetworkApi.unlikeImage(String.valueOf(baseImage.id))
                .map(likeImageResponse -> likeImageResponse != null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void deleteImage(BaseImage baseImage) {
        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.deleteImage(String.valueOf(baseImage.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseStateResponse -> {
                    imageCommentActivityView.viewImageProgress(false);
                    imageCommentActivityView.onImageDeleted(baseImage, true);
                }, throwable -> {
                    imageCommentActivityView.viewImageProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

}
