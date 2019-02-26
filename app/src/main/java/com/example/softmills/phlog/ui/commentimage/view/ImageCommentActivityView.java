package com.example.softmills.phlog.ui.commentimage.view;


import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.ui.commentimage.model.ImageCommentsData;
import com.example.softmills.phlog.ui.commentimage.model.SubmitImageCommentData;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface ImageCommentActivityView {
    void viewImageProgress(Boolean state);

    void viewPhotoComment(ImageCommentsData imageCommentsData);

    //    void viewHeaderImageProgress(boolean state);
    void onImageCommented(SubmitImageCommentData commentData);

    void onImageLiked(BaseImage baseImage);

    void viewOnImagedAddedToCart(boolean state);

    void viewOnImageRate(BaseImage baseImage);

    void viewMessage(String msg);

    void onImageDeleted(BaseImage baseImage,boolean state);
}
