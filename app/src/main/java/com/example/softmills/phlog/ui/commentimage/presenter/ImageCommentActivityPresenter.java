package com.example.softmills.phlog.ui.commentimage.presenter;


import com.example.softmills.phlog.base.commonmodel.BaseImage;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface ImageCommentActivityPresenter {
    void getImageComments(String imageId, String page);
    void likePhoto(BaseImage baseImage);
    void unLikePhoto(BaseImage baseImage);

    void submitComment(String imageId, String comment);
}
