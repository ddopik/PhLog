package com.example.softmills.phlog.ui.commentimage.presenter;


import com.example.softmills.phlog.base.commonmodel.BaseImage;

import io.reactivex.Observable;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface ImageCommentActivityPresenter {
    void getImageComments(String imageId, String page);

    Observable<Boolean> likePhoto(BaseImage baseImage);

    Observable<Boolean> unLikePhoto(BaseImage baseImage);

    void deleteImage(BaseImage baseImage);

    void submitComment(String imageId, String comment);
}
