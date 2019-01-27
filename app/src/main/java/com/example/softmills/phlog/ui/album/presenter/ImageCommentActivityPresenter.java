package com.example.softmills.phlog.ui.album.presenter;

import com.example.softmills.phlog.base.commonmodel.BaseImage;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface ImageCommentActivityPresenter {
    void getImageComments(String imageId,String page);
    void likePhoto(String imageId);
    void submitComment(String imageId, String comment);

    Observable<Boolean> savePhoto(BaseImage image);

    Observable<Boolean> unSavePhoto(BaseImage image);
}
