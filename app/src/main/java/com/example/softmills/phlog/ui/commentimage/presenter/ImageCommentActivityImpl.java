package com.example.softmills.phlog.ui.commentimage.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.commentimage.view.ImageCommentActivityView;

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
    public void getImageComments(String imageId, String page) {
        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.getImageComments(imageId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumImgCommentResponse -> {
                    imageCommentActivityView.viewPhotoComment(albumImgCommentResponse.data);
                    imageCommentActivityView.viewImageProgress(false);
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
                    imageCommentActivityView.viewMessage("Comment Submitted");
                    imageCommentActivityView.onImageCommented(submitImageCommentResponse.data);
                    imageCommentActivityView.viewImageProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    imageCommentActivityView.viewImageProgress(false);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void likePhoto(BaseImage baseImage) {
        imageCommentActivityView.viewImageProgress(true);


        BaseNetworkApi.likeImage(String.valueOf(baseImage.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(likeImageResponse -> {
                    imageCommentActivityView.onImageLiked(likeImageResponse.data);
                    imageCommentActivityView.viewImageProgress(false);
                }, throwable -> {
                    imageCommentActivityView.viewImageProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });


    }

    @SuppressLint("CheckResult")
    @Override
    public void unLikePhoto(BaseImage baseImage) {
        BaseNetworkApi.unlikeImage(String.valueOf(baseImage.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(likeImageResponse -> {
                    imageCommentActivityView.onImageLiked(baseImage);
                    imageCommentActivityView.onImageLiked(likeImageResponse.data);
                }, throwable -> {
                    imageCommentActivityView.viewImageProgress(false);
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
